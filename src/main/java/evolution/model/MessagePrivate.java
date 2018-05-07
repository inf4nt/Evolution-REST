package evolution.model;
import lombok.Data;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message_private")
@Data
public class MessagePrivate {

    @Id
    @Column(columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_message_private")
    @SequenceGenerator(name = "seq_message_private", sequenceName = "seq_message_private_id", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id", columnDefinition = "bigint", nullable = false, updatable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id", columnDefinition = "bigint", nullable = false, updatable = false)
    private User recipient;

    @Column(name = "text", nullable = false, columnDefinition = "text")
    private String text;

    @Column(name = "date_post", updatable = false, columnDefinition = "timestamp default current_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePost;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive;

    @Column(name = "is_read", columnDefinition = "boolean default false")
    private boolean isRead;

    @Version
    @Column(columnDefinition = "bigint default 0")
    private Long version;
}
