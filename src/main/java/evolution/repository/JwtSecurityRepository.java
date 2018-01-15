package evolution.repository;

import evolution.security.model.AuthenticationSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JwtSecurityRepository extends JpaRepository<AuthenticationSession, String> {

    @Query("select a from AuthenticationSession a where a.authSession =:sessionKey")
    Optional<AuthenticationSession> findBySessionKey(@Param("sessionKey") String sessionKey);

    @Query("select a from AuthenticationSession a where a.username = :username")
    Optional<AuthenticationSession> findByUsername(@Param("username") String username);
}
