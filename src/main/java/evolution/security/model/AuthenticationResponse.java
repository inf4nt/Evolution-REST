package evolution.security.model;

import evolution.model.User;
import lombok.*;

/**
 * Created by Infant on 15.08.2017.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private User user;
}