package evolution.security.model;

import evolution.model.user.UserLight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Infant on 15.08.2017.
 */

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class AuthenticationResponse {
    private String token;
    private UserLight user;
}