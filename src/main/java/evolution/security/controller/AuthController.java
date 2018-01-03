package evolution.security.controller;


import evolution.security.model.AuthenticationRequest;
import evolution.security.model.AuthenticationResponse;
import evolution.security.model.JwtCleanToken;
import evolution.security.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Infant on 15.08.2017.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
        return authenticationService.authenticationRequest(authenticationRequest);
    }

    @PostMapping(value = "/admin")
    public ResponseEntity<AuthenticationResponse> authenticationRequestAdmin(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
        return authenticationService.authenticationRequestAdmin(authenticationRequest);
    }

    @PostMapping("/clean-auth-session")
    public ResponseEntity<HttpStatus> cleanToken(@RequestBody JwtCleanToken jwtCleanToken) {
        return authenticationService.cleanAuthenticationSessionKey(jwtCleanToken);
    }

    @PostMapping("/refresh-auth-session")
    public ResponseEntity<AuthenticationResponse> refreshAuthenticationSessionKey(@RequestBody JwtCleanToken jwtCleanToken) {
        return refreshAuthenticationSessionKey(jwtCleanToken);
    }
}
