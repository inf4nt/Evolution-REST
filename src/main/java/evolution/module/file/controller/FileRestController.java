package evolution.module.file.controller;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import evolution.file.api.FileService;
import evolution.file.api.GoogleDrive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping(value = "/file")
@CrossOrigin
public class FileRestController {

    private final FileService fileService;

    private final GoogleDrive googleDrive;

    @Autowired
    public FileRestController(FileService fileService,
                              GoogleDrive googleDrive) {
        this.fileService = fileService;
        this.googleDrive = googleDrive;
    }

    @GetMapping
    public ResponseEntity<List<File>> getAllFile() {
//        return ResponseEntity.ok(fileService.getAllFiles().getFiles());
        return null;
    }

    @GetMapping(value = "/{fileKey}")
    public ResponseEntity<String> getLinkByFleKey(@PathVariable String fileKey) {
        return null;
    }

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam(name = "file") MultipartFile multipartFile) {
        return null;
    }

    @GetMapping(value = "/download/{fileKey}")
    public ResponseEntity<String> downloadFileByKey(@PathVariable String fileKey) {
        return null;
    }

    @GetMapping(value = "/delete/all")
    public ResponseEntity<HttpStatus> deleteAll() {
//        fileService.deleteAll();
//        return ResponseEntity.ok().build();
        return null;
    }

























    @GetMapping(value = "/test")
    public void test() throws IOException {
        Drive drive = googleDrive.drive();
        FileList result = drive.files().list()
                .setFields("nextPageToken, files(id, name, parents, webViewLink )")
                .execute();
        List<File> files = result.getFiles();


        for (int i = 0; i < files.size(); i++) {
            System.out.println(i + " " + files.get(i));
        }

        System.out.println("===================================");
    }


    @GetMapping(value = "/test2")
    public Object test2() throws IOException {
        Drive drive = googleDrive.drive();
        FileList result = drive.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name, parents, webViewLink, webContentLink, iconLink)")
                .execute();
        List<File> files = result.getFiles();
        File file = files.get(0);


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        drive.files().get(file.getId()).executeMediaAndDownloadTo(outputStream);
        byte [] b = outputStream.toByteArray();
        byte[] encoded = Base64.encodeBase64(b);
        String encodedString = new String(encoded);

        if (file.getPermissions() != null) {
            file.getPermissions().forEach(System.out::println);
        }

        String link = "https://docs.google.com/uc?id=" + file.getWebViewLink();

        String res = "data:image/jpg;base64," + encodedString;
//        https://drive.google.com/file/d/13Cbi9RMmHLmwY5_fUIOhny3n1kqUVvmn/view?usp=drivesdk
//        13Cbi9RMmHLmwY5_fUIOhny3n1kqUVvmn
        return file.getWebContentLink();
    }

    @GetMapping(value = "/upload")
    public String upload() throws IOException {
        java.io.File file = new java.io.File("/E-Nexc_YR2I.jpg");
        return fileService.uploadSharedFile(file);

//        File fileMetadata = new File();
//        fileMetadata.setName("/E-Nexc_YR2I.jpg");
//        java.io.File filePath = new java.io.File("/E-Nexc_YR2I.jpg");
//        FileContent mediaContent = new FileContent("image/jpeg", filePath);
//        File file = googleDrive.getDrive().files().create(fileMetadata, mediaContent)
//                .setFields("id")
//                .execute();
//        System.out.println("File ID: " + file.getId());
//        return file.getId();
    }

}
