package evolution.module.message.model;

import evolution.module.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChannelUserReferenceEmbeddable implements Serializable {

        @ManyToOne
        @JoinColumn(name = "user_id", columnDefinition = "bigint")
        private User user;

        @ManyToOne
        @JoinColumn(name = "channel_id", columnDefinition = "bigint")
        private Channel channel;
    }

}
