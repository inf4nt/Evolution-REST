package evolution.security.controller;

import evolution.common.UserRoleEnum;
import evolution.model.User;
import evolution.security.TokenUtil;
import evolution.security.model.AuthenticationRequest;
import evolution.security.model.AuthenticationResponse;
import evolution.security.model.CustomSecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, TokenUtil tokenUtils, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
        this.userDetailsService = userDetailsService;
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
        String token = this.tokenUtils.generateToken(userDetails);

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

}
