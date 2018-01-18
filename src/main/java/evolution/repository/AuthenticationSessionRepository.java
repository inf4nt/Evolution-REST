package evolution.repository;

import evolution.model.AuthenticationSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface AuthenticationSessionRepository extends JpaRepository<AuthenticationSession, String> {

    @Query("select a from AuthenticationSession a where a.authSession =:sessionKey")
    Optional<AuthenticationSession> findBySessionKey(@Param("sessionKey") String sessionKey);

    @Async
    @Query("select a from AuthenticationSession a where a.authSession =:sessionKey")
    CompletableFuture<AuthenticationSession> findBySessionKeyAsync(@Param("sessionKey") String sessionKey);

    @Query("select a from AuthenticationSession a where a.username = :username")
    Optional<AuthenticationSession> findByUsername(@Param("username") String username);
}
