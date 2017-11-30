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
    SEND_REQUEST_TO_FRIEND(4),
    NO_ACTION(5);

    private final int id;
}
