package evolution.file.api;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.Drive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Service
public class GoogleDrive {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** Application name. */
    private static final String APPLICATION_NAME =
            "Drive API Java Quickstart";

    /** Directory to store user google.drive.credentials for this application. */
//    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), "google/drive/google-drive.json");
    private static final java.io.File DATA_STORE_DIR = new java.io.File("/google/drive/");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved google.drive.credentials
     * at ~/.google.drive.credentials/drive-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(DriveScopes.DRIVE);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                GoogleDrive.class.getResourceAsStream("/google/drive/client_secret.json");
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
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * @throws IOException
     */
    public static Drive getDriveService() throws IOException {
        Credential credential = authorize();
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public Drive getDrive() throws IOException {
        return getDriveService();
    }


//    private File getFileByName(String fileName){
//        File resultFile = null;
//        String searchQuery;
//        again:
//        for(String extension: extensions.keySet()){
//            searchQuery = "mimeType='"+extensions.get(extension)+"' and name='" + fileName + extension+"'";
//            String pageToken = null;
//            do {
//                FileList result = null;
//                try {
//                    result = driveService.files().list()
//                            .setQ(searchQuery)
//                            .setSpaces("drive")
//                            .setFields("nextPageToken, files(id, name, parents, webViewLink )")
//                            .setPageToken(pageToken)
//                            .execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if(result==null) break again;
//                for (File file : result.getFiles()) {
//                    resultFile = file;
//                    break ;
//                }
//                pageToken = result.getNextPageToken();
//            } while (pageToken != null);
//        }
//        return resultFile;
//    }
//    public String getShareableLink(String fileName){
//        File file = getFileByName(fileName);
//        return file != null ? file.getWebViewLink() : null;
//    }
//    public String getFileId(String fileName){
//        return getFileByName(fileName).getId();
//    }
//    private boolean upload(FileContent mediaContent, File fileMetadata){
//        try {
//            driveService.files().create(fileMetadata, mediaContent)
//                    .setFields("id, parents")
//                    .execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//    public boolean uploadAudio(java.io.File uploadingFile){
//        File fileMetadata = new File();
//        fileMetadata.setName(uploadingFile.getName());
//        String folderId = getFileId("audio");
//        fileMetadata.setParents(Collections.singletonList(folderId));
//        FileContent mediaContent = new FileContent("audio/mpeg", uploadingFile);
//
//        upload(mediaContent,fileMetadata);
//
//        return true;
//    }
//
//    public boolean uploadJSON(java.io.File uploadingFile) {
//        File fileMetadata = new File();
//        fileMetadata.setName(uploadingFile.getName());
//        String folderId = getFileId("LibraAudio");
//        fileMetadata.setParents(Collections.singletonList(folderId));
//        FileContent mediaContent = new FileContent("application/json", uploadingFile);
//
//        upload(mediaContent,fileMetadata);
//        return true;
//    }
//
//    public boolean getJSON(String fileName){
//        download(fileName);
//        return true;
//    }
//    public boolean uploadAvatar(java.io.File uploadingAvatar){
//        String fileAbsolutePath = uploadingAvatar.getAbsolutePath();
//        String mime = "";
//        for(String extension: extensions.keySet()){
//            if(extension.equals("")) continue;
//            if(fileAbsolutePath.contains(extension)){
//                mime = extensions.get(extension);
//                break;
//            }
//        }
//        File fileMetadata = new File();
//        fileMetadata.setName(uploadingAvatar.getName());
//        String folderId = getFileId("Avatars");
//        fileMetadata.setParents(Collections.singletonList(folderId));
//        FileContent mediaContent = new FileContent(mime, uploadingAvatar);
//        upload(mediaContent,fileMetadata);
//        return  true;
//    }
//    public boolean remove(String fileName){
//        String fileId = getFileId(fileName);
//        try {
//            driveService.files().delete(fileId).execute();
//        } catch (IOException e) {
//            System.out.println("An error occurred: " + e);
//            return false;
//        }
//        return true;
//
//    }
//    public boolean uploadText(java.io.File uploadingFile){
//        File fileMetadata = new File();
//        fileMetadata.setName(uploadingFile.getName());
//        String folderId = getFileId("LibraAudio");
//        fileMetadata.setParents(Collections.singletonList(folderId));
//        FileContent mediaContent = new FileContent("text/plain", uploadingFile);
//
//        upload(mediaContent,fileMetadata);
//        return true;
//    }
//    public boolean download(String fileName) {
//        String fileId = getFileId(fileName);
//        if (!fileId.equals("-1")) {
//            OutputStream outputStream = new ByteArrayOutputStream();
//            try {
//                driveService.files().get(fileId).executeMediaAndDownloadTo(outputStream);
//                FileOutputStream downloadedFile = new FileOutputStream(fileName);
//                ((ByteArrayOutputStream) outputStream).writeTo(downloadedFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return false;
//            }
//        } else {
//            System.out.println("Sorry there is no file with such name!");
//            return false;
//        }
//        return true;
//    }

}