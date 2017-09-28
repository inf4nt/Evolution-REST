package evolution.common;

/**
 * Created by Admin on 03.03.2017.
 */
public enum UserRoleEnum {

    USER(1L),
    ADMIN(2L);

    public final Long id;

    UserRoleEnum(Long i) {
        this.id = i;
    }

    public UserRoleEnum get (){
        return this;
    }

    public Long getId() {
        return id;
    }

}
