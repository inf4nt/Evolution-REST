package evolution.crud;

import evolution.crud.api.AuthenticationSessionCrudManagerService;
import evolution.model.AuthenticationSession;
import evolution.repository.AuthenticationSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthenticationSessionCrudManagerServiceImpl implements AuthenticationSessionCrudManagerService {

    private final AuthenticationSessionRepository authenticationSessionRepository;

    @Autowired
    public AuthenticationSessionCrudManagerServiceImpl(AuthenticationSessionRepository authenticationSessionRepository) {
        this.authenticationSessionRepository = authenticationSessionRepository;
    }

    @Override
    public Optional<AuthenticationSession> findByAuthSessionKey(String sessionKey) {
        return authenticationSessionRepository.findBySessionKey(sessionKey);
    }

    @Override
    public Optional<AuthenticationSession> findByUsername(String username) {
        return authenticationSessionRepository.findByUsername(username);
    }

    @Override
    public AuthenticationSession save(AuthenticationSession jwtSecurity) {
        return authenticationSessionRepository.save(jwtSecurity);
    }

    @Override
    @Transactional
    public void delete(String username) {
        Optional<AuthenticationSession> op = authenticationSessionRepository.findByUsername(username);
        op.ifPresent(o -> authenticationSessionRepository.delete(o));
    }

    @Override
    public void delete(AuthenticationSession jwtSecurity) {
        authenticationSessionRepository.delete(jwtSecurity);
    }
}
