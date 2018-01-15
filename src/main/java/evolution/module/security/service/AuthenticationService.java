package evolution.security.service;

import evolution.security.dto.AuthenticationRequest;
import evolution.security.dto.AuthenticationResponse;
import evolution.security.dto.JwtCleanToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService {

    ResponseEntity<AuthenticationResponse> authenticationRequest(AuthenticationRequest authenticationRequest) throws AuthenticationException;

    ResponseEntity<AuthenticationResponse> authenticationRequestAdmin(AuthenticationRequest authenticationRequest) throws AuthenticationException;

    ResponseEntity<HttpStatus> cleanAuthenticationSessionKey(JwtCleanToken jwtCleanToken);

    ResponseEntity<AuthenticationResponse> refreshAuthenticationSessionKey(JwtCleanToken jwtCleanToken);
}
