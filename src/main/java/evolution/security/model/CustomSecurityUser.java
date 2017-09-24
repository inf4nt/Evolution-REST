package evolution.security.model;

import evolution.model.user.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

/**
 * Created by Infant on 15.08.2017.
 */
@ToString @Getter
public class CustomSecurityUser extends org.springframework.security.core.userdetails.User{

    private final User user;
    private Date lastPasswordReset;

    public CustomSecurityUser(
            String username, String password,
            boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities,
            User user, Date lastPasswordReset) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
        this.lastPasswordReset = lastPasswordReset;
    }


}
