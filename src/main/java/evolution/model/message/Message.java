package evolution.model.message;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import evolution.model.dialog.Dialog;
import evolution.model.user.UserLight;
import evolution.service.serialization.CustomMessageSerializerDialog;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by Admin on 09.06.2017.
 */

@Entity
@Table(name = "message")
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Message {

    @Id
    @Column(name = "message_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_message")
    @SequenceGenerator(name = "seq_message", sequenceName = "seq_message_id", allocationSize = 1)
    private Long id;

    @JsonSerialize(using = CustomMessageSerializerDialog.class)
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "dialog_id", updatable = false, nullable = false)
    private Dialog dialog;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    private UserLight sender;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "date_dispatch", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDispatch;

    public Message(Long messageId, String message, Date dateDispatch,
                   Long senderId, String senderFirstName, String senderLastName, Long dialogId) {
        this.dialog = new Dialog(dialogId);
        this.sender = new UserLight(senderId, senderFirstName, senderLastName);
        this.id = messageId;
        this.message = message;
        this.dateDispatch = dateDispatch;
    }

    public Message(Long messageId, String message, Date dateDispatch,
                   Long senderId, String senderFirstName, String senderLastName) {
        this.sender = new UserLight(senderId, senderFirstName, senderLastName);
        this.id = messageId;
        this.message = message;
        this.dateDispatch = dateDispatch;
    }

    public Message(Long dialogId, Long messageId, String message, Date dateDispatch,
                   Long senderId, String senderFirstName, String senderLastName,
                   Long imId, String imFirstName, String imLastName) {
        this.dialog = new Dialog(dialogId, new UserLight(imId, imFirstName, imLastName));
        this.sender = new UserLight(senderId, senderFirstName, senderLastName);
        this.id = messageId;
        this.message = message;
        this.dateDispatch = dateDispatch;
    }

    public Message(Long senderId, String message, Date dateDispatch, Long dialogId) {
        this.dialog = new Dialog(dialogId);
        this.sender = new UserLight(senderId);
        this.message = message;
        this.dateDispatch = dateDispatch;
    }

    public Message(UserLight sender, String message, Date dateDispatch, Dialog dialog) {
        this.dialog = dialog;
        this.sender = sender;
        this.message = message;
        this.dateDispatch = dateDispatch;
    }

    public Message(Long dialogId, UserLight sender, String message, Date dateDispatch) {
        this.dialog = new Dialog(dialogId);
        this.sender = sender;
        this.message = message;
        this.dateDispatch = dateDispatch;

    }

    public Message(UserLight sender, String message, Date dateDispatch) {
        this.sender = sender;
        this.message = message;
        this.dateDispatch = dateDispatch;

    }

    public Message(Long id) {
        this.id = id;
    }
}

