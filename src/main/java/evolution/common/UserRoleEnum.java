package evolution.common;

/**
 * Created by Admin on 03.03.2017.
 */
public enum UserRoleEnum {

    USER(1),
    ADMIN(2);

    public final long id;

    UserRoleEnum(long i) {
        this.id = i;
    }

    public UserRoleEnum get (){
        return this;
    }

    public long getId() {
        return id;
    }

}
