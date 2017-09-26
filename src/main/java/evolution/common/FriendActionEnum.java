package evolution.common;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Admin on 01.06.2017.
 */
@Getter
@AllArgsConstructor
public enum FriendActionEnum {

    ACCEPT_REQUEST(1),
    DELETE_FRIEND(2),
    DELETE_REQUEST(3),
    REQUEST_FRIEND(4);

    private final int id;
}
