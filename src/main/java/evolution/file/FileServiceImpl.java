package evolution.file;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import evolution.file.api.FileService;
import evolution.file.api.GoogleDrive;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    private final GoogleDrive googleDrive;

    @Autowired
    public FileServiceImpl(GoogleDrive googleDrive) {
        this.googleDrive = googleDrive;
    }

    @Override
    public String uploadFile(File file, Permission permission) {
        FileContent fileContent = googleDrive.createFileContent(file);
        com.google.api.services.drive.model.File f = new com.google.api.services.drive.model.File();
        f.setName(file.getName());

        Permission per = new Permission();
        per.setType("anyone");
        per.setRole("reader");

        return googleDrive.create(f, fileContent, permission);
    }

    @Override
    @SneakyThrows
    public String uploadFile(MultipartFile multipartFile, Permission permission) {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();

        return uploadFile(file, permission);
    }

    @Override
    public String uploadFile(File file) {
        FileContent fileContent = googleDrive.createFileContent(file);
        com.google.api.services.drive.model.File f = new com.google.api.services.drive.model.File();
        f.setName(file.getName());
        return googleDrive.create(f, fileContent);
    }

    @Override
    @SneakyThrows
    public String uploadFile(MultipartFile multipartFile) {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();

        return uploadFile(file);
    }

    @Override
    public File getFileByKey(String fileKey) {
        return null;
    }

    @Override
    public String getLinkByFileKey(String fileKey) {
        return googleDrive.getShareLinkByFileId(fileKey);
    }

    @Override
    public String generateFileKey() {
        return null;
    }

    @Override
    public List<String> getAllFileLink() {
        return googleDrive.getAllFiles()
                .getFiles()
                .stream()
                .map(o -> o.getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllFileId() {
        return googleDrive.getAllFiles()
                .getFiles()
                .stream()
                .map(o -> o.getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<File> getAllFile() {
        return null;
    }

    @Override
    public void deleteAll() {
        googleDrive.deleteAll();
    }

    @Override
    public void delete(String fileKey) {
        googleDrive.delete(fileKey);
    }
}
