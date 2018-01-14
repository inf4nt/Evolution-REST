package evolution.file.api;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import java.util.List;

public interface GoogleDrive {

    Drive drive();

    File create2(File file, FileContent fileContent);

    File create2(File file, FileContent fileContent, Permission permission);

    String create(File file, FileContent fileContent);

    String create(File file, FileContent fileContent, Permission permission);

    File update(String id, File file);

    void delete(String id);

    void deleteAll();

    File createPermission(String id, Permission permission);

    File createPermission(String id, List<Permission> permissions);

    List<Permission> getPermissionByFileId(String id);

    FileList getAllFiles();

    FileContent createFileContent(java.io.File file);

    String getShareLinkByFileId(String id);

    String getShareLinkByFileId(File file);
}
