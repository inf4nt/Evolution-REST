package evolution.module.security.crud.api;

import evolution.security.model.AuthenticationSession;

import java.util.Optional;

public interface AuthenticationSessionCrudManagerService {

    Optional<AuthenticationSession> findByAuthSessionKey(String sessionKey);

    Optional<AuthenticationSession> findByUsername(String username);

    AuthenticationSession save(AuthenticationSession authenticationSession);

    void delete(String username);

    void delete(AuthenticationSession authenticationSession);
}
