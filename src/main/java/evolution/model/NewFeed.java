package evolution.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Infant on 03.10.2017.
 */
//@Entity
//@Table(name = "feed")
@NoArgsConstructor
@ToString
@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private NewUser sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_user_id", columnDefinition = "bigint")
    private NewUser toUser;

    @Column(columnDefinition = "text")
    private String tags;

    @Column(name = "is_active")
    private boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewFeed newFeed = (NewFeed) o;

        if (isActive != newFeed.isActive) return false;
        if (id != null ? !id.equals(newFeed.id) : newFeed.id != null) return false;
        if (content != null ? !content.equals(newFeed.content) : newFeed.content != null) return false;
        if (date != null ? !date.equals(newFeed.date) : newFeed.date != null) return false;
        if (sender != null ? !sender.equals(newFeed.sender) : newFeed.sender != null) return false;
        if (toUser != null ? !toUser.equals(newFeed.toUser) : newFeed.toUser != null) return false;
        return tags != null ? tags.equals(newFeed.tags) : newFeed.tags == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (toUser != null ? toUser.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }
}
