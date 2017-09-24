package evolution.model.userToken;


import evolution.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Admin on 24.05.2017.
 */
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class UserToken extends AbstractToken {

    public UserToken(String token, User user) {
        super(token);
        this.user = user;
    }

    private User user;
}
