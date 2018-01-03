package evolution.security.token;

import evolution.model.AuthenticationSession;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtToken {

    String generateToken(String username, AuthenticationSession authenticationSession);

    String generateToken(String username, String authenticationSessionKey);

    String generateToken(String username);

    String getUsername(String token);

    String getAuthenticationSession(String token);

    boolean isValidToken(String token, UserDetails userDetails);

    String refreshToken(String token);
}
