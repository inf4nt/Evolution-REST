package evolution.module.security.crud;

import evolution.module.security.crud.api.AuthenticationSessionCrudManagerService;
import evolution.security.model.AuthenticationSession;
import evolution.module.security.repository.JwtSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthenticationSessionCrudManagerServiceImpl implements AuthenticationSessionCrudManagerService {

    private final JwtSecurityRepository jwtSecurityRepository;

    @Autowired
    public AuthenticationSessionCrudManagerServiceImpl(JwtSecurityRepository jwtSecurityRepository) {
        this.jwtSecurityRepository = jwtSecurityRepository;
    }

    @Override
    public Optional<AuthenticationSession> findByAuthSessionKey(String sessionKey) {
        return jwtSecurityRepository.findBySessionKey(sessionKey);
    }

    @Override
    public Optional<AuthenticationSession> findByUsername(String username) {
        return jwtSecurityRepository.findByUsername(username);
    }

    @Override
    public AuthenticationSession save(AuthenticationSession jwtSecurity) {
        return jwtSecurityRepository.save(jwtSecurity);
    }

    @Override
    @Transactional
    public void delete(String username) {
        Optional<AuthenticationSession> op = jwtSecurityRepository.findByUsername(username);
        op.ifPresent(o -> jwtSecurityRepository.delete(o));
    }

    @Override
    public void delete(AuthenticationSession jwtSecurity) {
        jwtSecurityRepository.delete(jwtSecurity);
    }
}
