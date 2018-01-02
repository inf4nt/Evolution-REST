package evolution.file.api;

import com.google.api.services.drive.Drive;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileService {

    boolean uploadFile(File file) throws IOException;

    String uploadFile(MultipartFile  file) throws IOException;

    File getFileByKey(String fileKey) throws IOException;

    String generateFileKey();
}
