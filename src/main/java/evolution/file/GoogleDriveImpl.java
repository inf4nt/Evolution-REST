package evolution.file;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import evolution.file.api.GoogleDrive;
import lombok.Getter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GoogleDriveImpl implements GoogleDrive {

    private static final Logger logger = LoggerFactory.getLogger(GoogleDriveImpl.class);

    /**
     * Application name.
     */
    private static final String APPLICATION_NAME =
            "Drive API Java Quickstart";

    /**
     * Directory to store user google.drive.credentials for this application.
     */
    private static final java.io.File DATA_STORE_DIR = new java.io.File("/google/drive/");

    /**
     * Global instance of the {@link FileDataStoreFactory}.
     */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;

    /**
     * Global instance of the scopes required by this quickstart.
     * <p>
     * If modifying these scopes, delete your previously saved google.drive.credentials
     * at ~/.google.drive.credentials/drive-java-quickstart
     */
    private final List<String> SCOPES;

    @Getter
    private final HashMap<String, String> extensions;

    public GoogleDriveImpl() {
                try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            logger.info("error " + t);
        }

        List<String> scopes = new ArrayList<>();
        scopes.add(DriveScopes.DRIVE);
        SCOPES = scopes;

        HashMap<String, String> extensions = new HashMap<>();
        extensions.put(".mp3", "audio/mpeg");
        extensions.put(".json", "application/json");
        extensions.put(".txt", "text/pain");
        extensions.put(".png", "image/png");
        extensions.put(".bmp", "image/x-windows-bmp");
        extensions.put(".jpg", "image/jpeg");
        extensions.put("", "application/vnd.google-apps.folder");
        this.extensions = extensions;
    }


    @SneakyThrows
    private Credential authorize() {
        InputStream in =
                GoogleDriveImpl.class.getResourceAsStream("/google/drive/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        logger.info("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    @Override
    @SneakyThrows
    public Drive drive() {
        Credential credential = authorize();
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    @Override
    @SneakyThrows
    public File create2(File file, FileContent fileContent) {
        return drive().files()
                .create(file, fileContent)
                .setFields("id")
                .execute();
    }

    @Override
    @SneakyThrows
    public File create2(File file, FileContent fileContent, Permission permission) {
        File f = create2(file, fileContent);
        return createPermission(f.getId(), permission);
    }

    @Override
    @SneakyThrows
    public File update(String id, File file) {
        return drive().files().update(id, file).execute();
    }

    @Override
    @SneakyThrows
    public String create(File file, FileContent fileContent) {
        File f = drive().files()
                .create(file, fileContent)
                .setFields("id")
                .execute();
        return getShareLinkByFileId(f.getId());
    }

    @Override
    @SneakyThrows
    public String create(File file, FileContent fileContent, Permission permission) {
        File f = drive().files()
                .create(file, fileContent)
                .setFields("id")
                .execute();
        return getShareLinkByFileId(createPermission(f.getId(), permission));
    }

    @Override
    public String createSharedFile(File file, FileContent fileContent) {
        Permission per = new Permission();
        per.setType("anyone");
        per.setRole("reader");

        return create(file, fileContent, per);
    }

    @Override
    @SneakyThrows
    public void delete(String id) {
        drive().files().delete(id).execute();
    }

    @Override
    @SneakyThrows
    public void deleteAll() {
        getAllFiles().getFiles().forEach(o -> delete(o.getId()));
    }

    @Override
    @SneakyThrows
    public File createPermission(String id, Permission permission) {
        drive().permissions().create(id, permission).execute();
        return drive().files().get(id).execute();
    }

    @Override
    @SneakyThrows
    public File createPermission(String id, List<Permission> permissions) {
        for (Permission permission : permissions) {
            drive().permissions().create(id, permission).execute();
        }
        return drive().files().get(id).execute();
    }

    @Override
    @SneakyThrows
    public List<Permission> getPermissionByFileId(String id) {
        return drive().files().get(id).execute().getPermissions();
    }

    @Override
    @SneakyThrows
    public FileList getAllFiles() {
        return drive().files()
                .list()
                .setFields("nextPageToken, files(id, name, parents, webViewLink, webContentLink, iconLink)")
                .execute();
    }

    @Override
    public FileContent createFileContent(java.io.File file) {
        String mime = "";
        for (String extension : getExtensions().keySet()) {
            if (extension.equals(""))
                continue;
            if (file.getAbsolutePath().contains(extension)) {
                mime = getExtensions().get(extension);
                break;
            }
        }

        return new FileContent(mime, file);
    }

    @Override
    public String getShareLinkByFileId(File file) {
        return "https://docs.google.com/uc?id=" + file.getId();
    }

    @Override
    @SneakyThrows
    public String getShareLinkByFileId(String id) {
        return getShareLinkByFileId(drive().files().get(id).execute());
    }
}