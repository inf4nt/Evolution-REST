package evolution.model.feed;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Infant on 27.07.2017.
 */
@Entity
@Table(name = "likes")
@NoArgsConstructor
@ToString
@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Like {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_likes")
    @SequenceGenerator(name = "seq_likes", sequenceName = "seq_likes_id", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    private User sender;
}
