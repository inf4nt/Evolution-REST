package evolution.module.security.controller;


import evolution.module.security.dto.AuthenticationResponse;
import evolution.module.security.service.AuthenticationService;
import evolution.security.dto.AuthenticationRequest;
import evolution.security.dto.JwtCleanToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/clean-auth-session")
    public ResponseEntity<HttpStatus> cleanToken(@RequestBody JwtCleanToken jwtCleanToken) {
        return authenticationService.cleanAuthenticationSessionKey(jwtCleanToken);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/refresh-auth-session")
    public ResponseEntity<AuthenticationResponse> refreshAuthenticationSessionKey(@RequestBody JwtCleanToken jwtCleanToken) {
        return authenticationService.refreshAuthenticationSessionKey(jwtCleanToken);
    }
}
