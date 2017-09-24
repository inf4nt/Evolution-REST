package evolution.model.feed;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.model.user.UserLight;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Infant on 26.07.2017.
 */
@Entity
@Table(name = "feed")
@NoArgsConstructor @ToString @Getter @Setter @AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Feed implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_feed")
    @SequenceGenerator(name = "seq_feed", sequenceName = "seq_feed_id", allocationSize = 1)
    private Long id;

    @Column(name = "feed_content")
    private String content;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "sender_id")
    private UserLight sender;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "to_user_id")
    private UserLight toUser;

    @Column
    private String tags;

    public String dateFormat() {
        return DateFormat.getInstance().format(date);
    }

    public List<String> listTags() {
        if (tags == null)
            return null;
        return Arrays
                .asList(tags.split("#"))
                .stream()
                .skip(1)
                .collect(Collectors.toList());
    }
}
