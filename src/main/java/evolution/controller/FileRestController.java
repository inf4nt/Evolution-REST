package evolution.controller;

import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import evolution.file.api.FileService;
import evolution.file.api.GoogleDrive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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


    @GetMapping(value = "/test2")
    public Object test2() throws IOException {
        Drive drive = googleDrive.getDrive();
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

//        data:image/gif;base64,

        return file.getWebContentLink();
    }

}
