package evolution.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import evolution.serialization.jackson.CustomMessageSerializerDialog;
import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Infant on 03.10.2017.
 */
@Entity
@Table(name = "message")
@Data
public class Message {

    @Id
    @Column(name = "id", columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_message")
    @SequenceGenerator(name = "seq_message", sequenceName = "seq_message_id", allocationSize = 1)
    private Long id;

//    @JsonSerialize(using = CustomMessageSerializerDialog.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dialog_id", updatable = false, nullable = false, columnDefinition = "bigint")
    private Dialog dialog;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id", columnDefinition = "bigint")
    private User sender;

    @Column(name = "message", nullable = false, columnDefinition = "text")
    private String message;

    @Column(name = "date_dispatch", nullable = false, columnDefinition = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDispatch;

    @Column(name = "is_active")
    private boolean isActive;

    @Version
    @Column(columnDefinition = "bigint")
    private Long version;
}
