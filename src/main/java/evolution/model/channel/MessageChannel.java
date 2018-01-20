package evolution.model.channel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import evolution.model.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "message_channel")
@Data
public class MessageChannel implements Serializable {

    @Id
    @Column(name = "id", columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_message_channel")
    @SequenceGenerator(name = "seq_message_channel", sequenceName = "seq_message_channel_id", allocationSize = 1)
    private Long id;

    @Column(columnDefinition = "text", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "sender_id", columnDefinition = "bigint", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "channel_id", columnDefinition = "bigint", nullable = false)
    private Channel channel;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive;

    @Column(name = "date_post", columnDefinition = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePost;

    @Version
    @Column(columnDefinition = "bigint")
    private Long version;
}
