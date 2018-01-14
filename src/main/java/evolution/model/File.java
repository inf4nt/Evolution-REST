//package evolution.model;
//
//import lombok.Data;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.util.Date;
//
//@Entity
//@Table(name = "file_data")
//@Data
//public class File {
//
//    @Id
//    @Column(name = "file_key", unique = true, updatable = false, columnDefinition = "varchar(255)")
//    private String fileKey;
//
//    @Column(name = "create_date", columnDefinition = "timestamp default current_timestamp")
//    private Date createDate;
//
//    @Column(name = "is_active", columnDefinition = "boolean default true")
//    private boolean isActive;
//}
