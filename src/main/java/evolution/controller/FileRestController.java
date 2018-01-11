package evolution.controller;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import evolution.file.api.FileService;
import evolution.file.api.GoogleDrive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
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

    @GetMapping(value = "/test")
    public void test() throws IOException {
        Drive drive = googleDrive.getDrive();
        FileList result = drive.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name, parents, webViewLink )")
                .execute();
        List<File> files = result.getFiles();


        if (files != null) {
            files.forEach(o -> System.out.println(o));

        }

        System.out.println("===================================");
    }

    @GetMapping(value = "/upload")
    public void upload() throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName("/E-Nexc_YR2I.jpg");
        java.io.File filePath = new java.io.File("/E-Nexc_YR2I.jpg");
        FileContent mediaContent = new FileContent("image/jpeg", filePath);
        File file = googleDrive.getDrive().files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        System.out.println("File ID: " + file.getId());
    }


}
