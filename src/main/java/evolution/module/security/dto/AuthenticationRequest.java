package evolution.security.dto;

import lombok.*;

/**
 * Created by Infant on 15.08.2017.
 */
@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}