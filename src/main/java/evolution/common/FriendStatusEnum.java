package evolution.common;

/**
 * Created by Admin on 23.03.2017.
 */
public enum FriendStatusEnum {

    PROGRESS(1L),
    FOLLOWER(2L),
    REQUEST(3L),
    NOT_FOUND(4L);

    public final Long id;

    FriendStatusEnum(Long i) {
        this.id = i;
    }

    public FriendStatusEnum get (){
        return this;
    }

    public Long getId() {
        return id;
    }
}
