package evolution.model;

import evolution.common.RelationshipStatus;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Infant on 03.10.2017.
 */
@Entity
@Table(name = "friends")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friend {

    @EmbeddedId
    private FriendEmbeddable pk;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RelationshipStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "action_user_id", columnDefinition = "bigint", nullable = false)
    private User actionUser;

    @Version
    @Column(columnDefinition = "bigint")
    private Long version;

    public Friend(RelationshipStatus status) {
        this.status = status;
    }

    public Friend(User first, User second, RelationshipStatus status, User actionUser) {
        this.pk = new FriendEmbeddable(first, second);
        this.status = status;
        this.actionUser = actionUser;
    }

    @PrePersist
    @PreUpdate
    public void prePersistUpdate() {
        if (pk.getSecond().getId() > pk.getFirst().getId() || pk.getSecond().getId().equals(pk.getFirst().getId())) {
            throw new UnsupportedOperationException("firstUserId always must be > secondUserId or second == first");
        }
    }

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FriendEmbeddable implements Serializable {

        /*
            FIRST_ID ALWAYS > SECOND_ID !!!!
                FIRST_ID ALWAYS > SECOND_ID !!!!
                    FIRST_ID ALWAYS > SECOND_ID !!!!
                        FIRST_ID ALWAYS > SECOND_ID !!!!
                            FIRST_ID ALWAYS > SECOND_ID !!!!
                                FIRST_ID ALWAYS > SECOND_ID !!!!
                                    FIRST_ID ALWAYS > SECOND_ID !!!!

         */

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "first_user_id", columnDefinition = "bigint", nullable = false)
        private User first;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "second_user_id", columnDefinition = "bigint", nullable = false)
        private User second;
    }
}
