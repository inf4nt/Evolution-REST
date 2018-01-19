package evolution.model.channel;

import evolution.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "channel_user_references")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelUserReference implements Serializable {

    @EmbeddedId
    private ChannelUserReferenceEmbeddable pk;

    public ChannelUserReference(Channel channel, User user) {
        this.pk = new ChannelUserReferenceEmbeddable(channel, user);
    }

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChannelUserReferenceEmbeddable implements Serializable {

        @ManyToOne
        @JoinColumn(name = "channel_id", columnDefinition = "bigint")
        private Channel channel;

        @ManyToOne
        @JoinColumn(name = "user_id", columnDefinition = "bigint")
        private User user;
    }

}
