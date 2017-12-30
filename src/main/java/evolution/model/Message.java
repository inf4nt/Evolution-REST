package evolution.model;


import lombok.*;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dialog_id", updatable = false, nullable = false, columnDefinition = "bigint")
    private Dialog dialog;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id", columnDefinition = "bigint")
    private User sender;

    @Column(name = "message", nullable = false, columnDefinition = "text")
    private String message;

    @Column(name = "date_dispatch", nullable = false, columnDefinition = "timestamp", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDispatch;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive;

    @Column(name = "is_read", columnDefinition = "boolean default false")
    private boolean isRead;

    @Version
    @Column(columnDefinition = "bigint")
    private Long version;
}
