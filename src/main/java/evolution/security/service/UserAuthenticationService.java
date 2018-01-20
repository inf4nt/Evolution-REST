package evolution.security.service;

import evolution.model.AuthenticationSession;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface UserAuthenticationService {


    CompletableFuture<Optional<UserDetails>> loadByUsernameAsync(String username);

    CompletableFuture<Optional<AuthenticationSession>> findAuthenticationSessionBySessionKeyAsync(String sessionKey);

    Optional<UserDetails> loadByUsername(String username);

    Optional<AuthenticationSession> findAuthenticationSessionBySessionKey(String sessionKey);
}
