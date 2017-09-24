package evolution.security.model;

import lombok.*;

/**
 * Created by Infant on 15.08.2017.
 */
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public class AuthenticationRequest {
    private String username;
    private String password;
}