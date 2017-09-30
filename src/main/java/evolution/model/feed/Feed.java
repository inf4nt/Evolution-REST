package evolution.model.feed;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.model.user.UserLight;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "sender_id")
    private UserLight sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "to_user_id")
    private UserLight toUser;

    @Column
    private String tags;

    @JsonGetter(value = "listTags")
    public List<String> listTags() {
        if (tags == null)
            return new ArrayList<>();
        return Arrays.stream(tags.split("#"))
                .skip(1)
                .collect(Collectors.toList());
    }
}
