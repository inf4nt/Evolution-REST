package evolution.security.token;

import evolution.security.model.AuthenticationSession;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {

    String generateToken(String username, AuthenticationSession authenticationSession);

    String generateToken(String username, String authenticationSessionKey);

    String generateToken(UserDetails userDetails, AuthenticationSession authenticationSession);

    String generateToken(UserDetails userDetails, String authenticationSessionKey);

    String generateToken(String username);

    String generateToken(UserDetails userDetails);

    String getUsername(String token);

    String getAuthenticationSession(String token);

    boolean isValidToken(String token, UserDetails userDetails);

    String refreshToken(String token);
}
