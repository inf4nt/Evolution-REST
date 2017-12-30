package evolution.crud.api;

import evolution.model.AuthenticationSession;

import java.util.Optional;

public interface AuthenticationSessionCrudManagerService {

    Optional<AuthenticationSession> findByAuthSessionKey(String sessionKey);

    Optional<AuthenticationSession> findByUsername(String username);

    AuthenticationSession save(AuthenticationSession authenticationSession);

    void delete(String username);

    void delete(AuthenticationSession authenticationSession);
}
