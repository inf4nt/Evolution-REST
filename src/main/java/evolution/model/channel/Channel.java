package evolution.model.channel;

import evolution.model.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "channel")
@Data
public class Channel implements Serializable {

    @Id
    @Column(name = "id", columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_channel")
    @SequenceGenerator(name = "seq_channel", sequenceName = "seq_channel_id", allocationSize = 1)
    private Long id;

    @Column(name = "channel_name", columnDefinition = "varchar(255)")
    private String channelName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "channel_user_references",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "channel_id")}
    )
    private List<User> channelUser = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "channel")
    private List<MessageChannel> messageChannelList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "who_created_channel_user_id", columnDefinition = "bigint")
    private User whoCreatedChannel;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive;

    @Column(name = "is_private", columnDefinition = "boolean default false")
    private boolean isPrivate;

    @Version
    @Column(columnDefinition = "bigint")
    private Long version;
}
