package evolution.model;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "authentication_session")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationSession implements Serializable {

    @Id
    @Column(columnDefinition = "varchar(255)")
    private String username;

    @Column(name = "auth_session_key", nullable = false, columnDefinition = "varchar(255)")
    private String authSession;

    @Column(name = "creation_date", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private Date creationDate;
}
