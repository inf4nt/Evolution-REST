package evolution.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Infant on 03.10.2017.
 */
@Entity
@Table(name = "feed")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Feed {

    @Id
    @Column(columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_feed")
    @SequenceGenerator(name = "seq_feed", sequenceName = "seq_feed_id", allocationSize = 1)
    private Long id;

    @Column(name = "feed_content", columnDefinition = "text")
    private String content;

    @Column(name = "date_create", columnDefinition = "timestamp", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id", columnDefinition = "bigint")
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_user_id", columnDefinition = "bigint")
    private User toUser;

    @Column(columnDefinition = "text")
    private String tags;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive;

    @Version
    @Column(columnDefinition = "bigint")
    private Long version;
}
