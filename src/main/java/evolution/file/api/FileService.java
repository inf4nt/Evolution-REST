package evolution.file.api;


import com.google.api.services.drive.model.Permission;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileService {

    String uploadFile(File file, Permission permission);

    String uploadFile(MultipartFile multipartFile, Permission permission);

    String uploadFile(File file);

    String uploadFile(MultipartFile multipartFile);

    String uploadSharedFile(File file);

    String uploadSharedFile(MultipartFile multipartFile);

    File getFileByKey(String fileKey);

    String getLinkByFileKey(String fileKey);

    String generateFileKey();

    List<String> getAllFileLink();

    List<String> getAllFileId();

    List<File> getAllFile();

    void delete(String fileKey);

    void deleteAll();

    File multipartFileToFile(MultipartFile multipartFile);
}
