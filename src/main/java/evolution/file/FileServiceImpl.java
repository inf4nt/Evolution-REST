package evolution.file;

import evolution.file.api.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public boolean uploadFile(File file) throws IOException {
        return false;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();
        return null;
    }

    @Override
    public File getFileByKey(String fileKey) throws IOException {
        return null;
    }

    @Override
    public String generateFileKey() {
        return null;
    }
}
