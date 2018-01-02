package evolution.model.channel;

import evolution.model.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "channel_user_references")
@Data
public class ChannelUserReference implements Serializable {

    @EmbeddedId
    private ChannelUserReferenceEmbeddable pk;

    @Embeddable
    @Data
    public static class ChannelUserReferenceEmbeddable implements Serializable {

        @ManyToOne
        @JoinColumn(name = "user_id", columnDefinition = "bigint")
        private User user;

        @ManyToOne
        @JoinColumn(name = "channel_id", columnDefinition = "bigint")
        private Channel channel;
    }

}
