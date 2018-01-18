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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationSessionRepository authenticationSessionRepository;

    @Autowired
    public UserAuthenticationServiceImpl(UserRepository userRepository,
                                         AuthenticationSessionRepository authenticationSessionRepository) {
        this.userRepository = userRepository;
        this.authenticationSessionRepository = authenticationSessionRepository;
    }

    @Override
    public CompletableFuture<Optional<UserDetails>> loadByUsername(String username) {
        CompletableFuture<User> cu = userRepository.findUserByUsernameAsync(username);
        return cu.thenApply(v -> {
            if (v != null) {
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
        });
    }

    @Override
    public CompletableFuture<Optional<AuthenticationSession>> findAuthenticationSessionBySessionKey(String sessionKey) {
        CompletableFuture<AuthenticationSession> ca = authenticationSessionRepository.findBySessionKeyAsync(sessionKey);
        return ca.thenApply(v -> Optional.ofNullable(v));
    }
}
