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
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GoogleDrive {

    private static final String APPLICATION_NAME = "Evolution social";
    private static final String CREDENTIALS_PATH = GoogleDrive.class.getClassLoader().getResource("/google/drive/.credentials").getPath();
    private static final java.io.File DATA_STORE_DIR = new java.io.File(CREDENTIALS_PATH);
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static HttpTransport HTTP_TRANSPORT;
    // Build a new authorized API client service.
    private static Drive driveService;
    private static HashMap<String, String> extensions = new HashMap<String, String>();

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
        try {
            driveService = getDriveService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        extensions.put(".mp3", "audio/mpeg");
        extensions.put(".json", "application/json");
        extensions.put(".txt", "text/pain");
        extensions.put(".png", "image/png");
        extensions.put(".bmp", "image/x-windows-bmp");
        extensions.put(".jpg", "image/jpeg");
        extensions.put("", "application/vnd.google-apps.folder");
    }

    private static Credential authorize() throws IOException {
        InputStream in = GoogleDrive.class.getResourceAsStream("/google/drive/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
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

    public static Drive getDriveService() throws IOException {
        Credential credential = authorize();
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
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