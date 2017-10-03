package evolution.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Infant on 03.10.2017.
 */
public class NewFeed {

    @Id
    @Column(columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_feed")
    @SequenceGenerator(name = "seq_feed", sequenceName = "seq_feed_id", allocationSize = 1)
    private Long id;

    @Column(name = "feed_content", columnDefinition = "text")
    private String content;

    @Column(name = "date_create")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id", columnDefinition = "bigint")
    private NewUserLight sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_user_id", columnDefinition = "bigint")
    private NewUserLight toUser;

    @Column(columnDefinition = "text")
    private String tags;

    @Column(name = "date_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdate;

    @Column(name = "is_active")
    private boolean isActive;
}
