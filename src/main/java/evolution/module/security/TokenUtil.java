package evolution.security;


import evolution.security.dto.CustomSecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Deprecated
public class TokenUtil {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.token.life}")
    private Long expiration;

    public String getUsernameFromToken(String token) {
        final Claims claims = this.getClaimsFromToken(token);
        return claims.getSubject();
    }

    public String getAuthSession(String token) {
        final Claims claims = this.getClaimsFromToken(token);
        return claims.get("authSession").toString();
    }

    public Date getCreatedDateFromToken(String token) {
        final Claims claims = this.getClaimsFromToken(token);
        return new Date((Long) claims.get("created"));
    }

    public Date getExpirationDateFromToken(String token) {
        final Claims claims = this.getClaimsFromToken(token);
        return claims.getExpiration();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(this.secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + this.expiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(this.generateCurrentDate());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        claims.put("created", this.generateCurrentDate());
        claims.put("authSession", UUID.randomUUID().toString());
        return this.generateToken(claims);
    }

    public String generateToken(UserDetails userDetails, String authSession) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        claims.put("created", this.generateCurrentDate());
        claims.put("authSession", authSession);
        return this.generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(this.generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = this.getCreatedDateFromToken(token);
        return (!(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset))
                && !(this.isTokenExpired(token)));
    }

    public String refreshToken(String token) {
        final Claims claims = this.getClaimsFromToken(token);
        claims.put("created", this.generateCurrentDate());
        return this.generateToken(claims);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        CustomSecurityUser user = (CustomSecurityUser) userDetails;
        final String username = this.getUsernameFromToken(token);
        final Date created = this.getCreatedDateFromToken(token);
        final String authSession = this.getAuthSession(token);
        return (username.equals(user.getUsername())
                && authSession != null
                && !(this.isTokenExpired(token))
                && !(this.isCreatedBeforeLastPasswordReset(created, user.getLastPasswordReset())));
    }

}