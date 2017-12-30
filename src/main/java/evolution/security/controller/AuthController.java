package evolution.security.controller;

import evolution.common.UserRoleEnum;
import evolution.crud.api.AuthenticationSessionCrudManagerService;
import evolution.model.AuthenticationSession;
import evolution.model.User;
import evolution.security.TokenUtil;
import evolution.security.model.AuthenticationRequest;
import evolution.security.model.AuthenticationResponse;
import evolution.security.model.CustomSecurityUser;
import evolution.security.model.JwtCleanToken;
import evolution.service.DateService;
import evolution.service.SecuritySupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by Infant on 15.08.2017.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final TokenUtil tokenUtils;

    private final UserDetailsService userDetailsService;

    private final AuthenticationSessionCrudManagerService authenticationSessionCrudManagerService;

    private final DateService dateService;

    private final SecuritySupportService securitySupportService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          TokenUtil tokenUtils,
                          UserDetailsService userDetailsService,
                          AuthenticationSessionCrudManagerService authenticationSessionCrudManagerService,
                          SecuritySupportService securitySupportService,
                          DateService dateService) {
        this.authenticationManager = authenticationManager;
        this.securitySupportService = securitySupportService;
        this.tokenUtils = tokenUtils;
        this.userDetailsService = userDetailsService;
        this.authenticationSessionCrudManagerService = authenticationSessionCrudManagerService;
        this.dateService = dateService;
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest)
            throws AuthenticationException {

        System.out.println("run auth " + authenticationRequest);

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        CustomSecurityUser userDetails = (CustomSecurityUser) this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token;
        String authSession;

        Optional<AuthenticationSession> op = authenticationSessionCrudManagerService.findByUsername(userDetails.getUsername());
        if (!op.isPresent()) {
            token = this.tokenUtils.generateToken(userDetails);
            authSession = tokenUtils.getAuthSession(token);
            AuthenticationSession jwt = new AuthenticationSession(userDetails.getUsername(), authSession, dateService.getCurrentDateInUTC());
            authenticationSessionCrudManagerService.save(jwt);
        } else {
            token = this.tokenUtils.generateToken(userDetails, op.get().getAuthSession());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("TOKEN " + token);
        return ResponseEntity.ok(new AuthenticationResponse(token, new User(userDetails)));
    }

    @PostMapping(value = "/admin")
    public ResponseEntity<AuthenticationResponse> authenticationRequestAdmin(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
        ResponseEntity<AuthenticationResponse> responseEntity = authenticationRequest(authenticationRequest);
        if (responseEntity.getBody().getUser().getRole() != UserRoleEnum.ADMIN) {
            return ResponseEntity.status(403).build();
        }
        return responseEntity;
    }

    @PostMapping("/clean-auth-session")
    public ResponseEntity cleanToken(@RequestBody JwtCleanToken jwtCleanToken) {
        Optional<CustomSecurityUser> optional = securitySupportService.getPrincipal();
        if (optional.isPresent()) {
            if (optional.get().getUser().getRole() == UserRoleEnum.ADMIN || optional.get().getUser().getUserAdditionalData().getUsername().equals(jwtCleanToken.getUsername())) {
                authenticationSessionCrudManagerService.delete(jwtCleanToken.getUsername());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(403).build();
            }
        } else {
            return ResponseEntity.status(204).build();
        }
    }
}
