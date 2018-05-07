//package evolution.file;
//
//import com.google.api.client.http.FileContent;
//import com.google.api.services.drive.model.Permission;
//import evolution.file.api.FileService;
//import evolution.file.api.GoogleDrive;
//import lombok.SneakyThrows;
//import org.apache.commons.io.FileUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.File;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class FileServiceImpl implements FileService {
//
//    private final GoogleDrive googleDrive;
//
//    @Autowired
//    public FileServiceImpl(GoogleDrive googleDrive) {
//        this.googleDrive = googleDrive;
//    }
//
//    @Override
//    public String uploadFile(File file, Permission permission) {
//        FileContent fileContent = googleDrive.createFileContent(file);
//        com.google.api.services.drive.model.File f = new com.google.api.services.drive.model.File();
//        f.setName(file.getName());
//        return googleDrive.create(f, fileContent, permission);
//    }
//
//    @Override
//    @SneakyThrows
//    public String uploadFile(MultipartFile multipartFile, Permission permission) {
//        return uploadFile(multipartFileToFile(multipartFile), permission);
//    }
//
//    @Override
//    public String uploadFile(File file) {
//        FileContent fileContent = googleDrive.createFileContent(file);
//        com.google.api.services.drive.model.File f = new com.google.api.services.drive.model.File();
//        f.setName(file.getName());
//        return googleDrive.create(f, fileContent);
//    }
//
//    @Override
//    @SneakyThrows
//    public String uploadFile(MultipartFile multipartFile) {
//        return uploadFile(multipartFileToFile(multipartFile));
//    }
//
//    @Override
//    public String uploadSharedFile(File file) {
//        FileContent fileContent = googleDrive.createFileContent(file);
//        com.google.api.services.drive.model.File f = new com.google.api.services.drive.model.File();
//        f.setName(file.getName());
//        return googleDrive.createSharedFile(f, fileContent);
//    }
//
//    @Override
//    public String uploadSharedFile(MultipartFile multipartFile) {
//        return uploadSharedFile(multipartFileToFile(multipartFile));
//    }
//
//    @Override
//    public File getFileByKey(String fileKey) {
//        return null;
//    }
//
//    @Override
//    public String getLinkByFileKey(String fileKey) {
//        return googleDrive.getShareLinkByFileId(fileKey);
//    }
//
//    @Override
//    public String generateFileKey() {
//        return null;
//    }
//
//    @Override
//    public List<String> getAllFileLink() {
//        return googleDrive.getAllFiles()
//                .getFiles()
//                .stream()
//                .map(com.google.api.services.drive.model.File::getId)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<String> getAllFileId() {
//        return googleDrive.getAllFiles()
//                .getFiles()
//                .stream()
//                .map(o -> o.getId())
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<File> getAllFile() {
//        return null;
//    }
//
//    @Override
//    public void deleteAll() {
//        googleDrive.deleteAll();
//    }
//
//    @Override
//    public void delete(String fileKey) {
//        googleDrive.delete(fileKey);
//    }
//
//    @Override
//    @SneakyThrows
//    public File multipartFileToFile(MultipartFile multipartFile) {
//        File file = new File(multipartFile.getOriginalFilename());
//        FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());
//        return file;
//    }
//}
