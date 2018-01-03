package evolution.security.token;

import evolution.model.AuthenticationSession;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtTokenImpl implements JwtToken {

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
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);
        claims.put("authSession", UUID.randomUUID().toString());
        return this.generateToken(claims);
    }

    @Override
    public String getUsername(String token) {
        String username;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    @Override
    public String getAuthenticationSession(String token) {
        String session;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            session = claims.get("authSession").toString();
        } catch (Exception e) {
            session = null;
        }
        return session;
    }

    @Override
    public boolean isValidToken(String token, UserDetails userDetails) {
        return false;
    }

    @Override
    public String refreshToken(String token) {
        return null;
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(null)
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
}
