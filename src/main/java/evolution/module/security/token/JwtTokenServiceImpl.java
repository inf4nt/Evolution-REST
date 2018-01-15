package evolution.security.token;

import evolution.security.model.AuthenticationSession;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.token.life}")
    private Long expiration;

    @Override
    public String generateToken(String username, AuthenticationSession authenticationSession) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);
        claims.put("authSession", authenticationSession.getAuthSession());
        return this.generateToken(claims);
    }

    @Override
    public String generateToken(String username, String authenticationSessionKey) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);
        claims.put("authSession", authenticationSessionKey);
        return this.generateToken(claims);
    }

    @Override
    public String generateToken(UserDetails userDetails, AuthenticationSession authenticationSession) {
        return generateToken(userDetails.getUsername(), authenticationSession);
    }

    @Override
    public String generateToken(UserDetails userDetails, String authenticationSessionKey) {
        return generateToken(userDetails.getUsername(), authenticationSessionKey);
    }

    @Override
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);
        return this.generateToken(claims);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername());
    }

    @Override
    public String getUsername(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims == null ? null : claims.getSubject();
    }

    @Override
    public String getAuthenticationSession(String token) {
        final Claims claims = this.getClaimsFromToken(token);
        return claims == null ? null : claims.get("authSession").toString();
    }

    @Override
    public boolean isValidToken(String token, UserDetails userDetails) {
        final String username = getUsername(token);
        final String authSession = getAuthenticationSession(token);
        return (username.equals(userDetails.getUsername())
                && authSession != null
        );
    }

    @Override
    public String refreshToken(String token) {
        throw new UnsupportedOperationException();
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(null)
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();
    }

    private Claims getClaimsFromToken(String token) {
        if (token != null && !token.isEmpty()) {
            return Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
        } else {
            return null;
        }

    }
}
