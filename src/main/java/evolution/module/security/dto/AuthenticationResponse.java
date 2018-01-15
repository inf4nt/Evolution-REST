package evolution.module.security.dto;

import evolution.module.user.dto.UserDTO;
import lombok.*;

/**
 * Created by Infant on 15.08.2017.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;

    private UserDTO user;
}