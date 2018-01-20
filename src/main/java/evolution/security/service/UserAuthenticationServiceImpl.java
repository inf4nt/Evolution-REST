package evolution.security.service;

import evolution.model.AuthenticationSession;
import evolution.model.User;
import evolution.repository.AuthenticationSessionRepository;
import evolution.repository.UserRepository;
import evolution.security.model.CustomSecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationSessionRepository authenticationSessionRepository;

    @Override
    public CompletableFuture<Optional<UserDetails>> loadByUsernameAsync(String username) {
        return CompletableFuture.supplyAsync(() -> loadByUsername(username));
    }

    @Override
    public CompletableFuture<Optional<AuthenticationSession>> findAuthenticationSessionBySessionKeyAsync(String sessionKey) {
        return CompletableFuture.supplyAsync(() -> findAuthenticationSessionBySessionKey(sessionKey));
    }

    @Override
    public Optional<UserDetails> loadByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent()) {
            User v = user.get();
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + v.getRole()));
            return Optional.of(new CustomSecurityUser(
                    v.getUserAdditionalData().getUsername(),
                    v.getUserAdditionalData().getPassword(),
                    true,
                    true,
                    true,
                    true,
                    grantedAuthorities,
                    v,
                    null
            ));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AuthenticationSession> findAuthenticationSessionBySessionKey(String sessionKey) {
        return authenticationSessionRepository.findBySessionKey(sessionKey);
    }
}
