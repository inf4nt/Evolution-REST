package evolution.security.service;

import evolution.security.model.AuthenticationRequest;
import evolution.security.model.AuthenticationResponse;
import evolution.security.model.JwtCleanToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService {

    ResponseEntity<AuthenticationResponse> authenticationRequest(AuthenticationRequest authenticationRequest) throws AuthenticationException;

    ResponseEntity<AuthenticationResponse> authenticationRequestAdmin(AuthenticationRequest authenticationRequest) throws AuthenticationException;

    ResponseEntity<HttpStatus> cleanAuthenticationSessionKey(JwtCleanToken jwtCleanToken);

    ResponseEntity<AuthenticationResponse> refreshAuthenticationSessionKey(JwtCleanToken jwtCleanToken);
}
