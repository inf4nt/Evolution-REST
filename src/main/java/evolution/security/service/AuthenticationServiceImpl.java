package evolution.security.service;

import evolution.common.UserRoleEnum;
import evolution.crud.api.AuthenticationSessionCrudManagerService;
import evolution.crud.api.UserCrudManagerService;
import evolution.dto.transfer.UserDTOTransferNew;
import evolution.model.AuthenticationSession;
import evolution.model.User;
import evolution.security.model.AuthenticationRequest;
import evolution.security.model.AuthenticationResponse;
import evolution.security.model.CustomSecurityUser;
import evolution.security.model.JwtCleanToken;
import evolution.security.token.JwtTokenService;
import evolution.service.DateService;
import evolution.service.SecuritySupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    private final UserDetailsService userDetailsService;

    private final AuthenticationSessionCrudManagerService authenticationSessionCrudManagerService;

    private final DateService dateService;

    private final SecuritySupportService securitySupportService;

    private final UserDTOTransferNew userDTOTransferNew;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     JwtTokenService jwtTokenService,
                                     UserDetailsService userDetailsService,
                                     AuthenticationSessionCrudManagerService authenticationSessionCrudManagerService,
                                     DateService dateService,
                                     SecuritySupportService securitySupportService,
                                     UserDTOTransferNew userDTOTransferNew) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
        this.authenticationSessionCrudManagerService = authenticationSessionCrudManagerService;
        this.dateService = dateService;
        this.securitySupportService = securitySupportService;
        this.userDTOTransferNew = userDTOTransferNew;
    }

    @Override
    @Transactional
    public ResponseEntity<AuthenticationResponse> authenticationRequest(AuthenticationRequest authenticationRequest) throws AuthenticationException {
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
            token = this.jwtTokenService.generateToken(userDetails);
            authSession = jwtTokenService.getAuthenticationSession(token);
            AuthenticationSession jwt = new AuthenticationSession(userDetails.getUsername(), authSession, dateService.getCurrentDateInUTC());
            authenticationSessionCrudManagerService.save(jwt);
        } else {
            token = this.jwtTokenService.generateToken(userDetails, op.get().getAuthSession());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("TOKEN " + token);
        return ResponseEntity.ok(new AuthenticationResponse(token, userDTOTransferNew.modelToDTO(userDetails)));
    }

    @Override
    @Transactional
    public ResponseEntity<AuthenticationResponse> authenticationRequestAdmin(AuthenticationRequest authenticationRequest) throws AuthenticationException {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        CustomSecurityUser userDetails = (CustomSecurityUser) this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        if (userDetails.getUser().getRole() != UserRoleEnum.ADMIN) {
            return ResponseEntity.status(403).build();
        }

        String token;
        String authSession;

        Optional<AuthenticationSession> op = authenticationSessionCrudManagerService.findByUsername(userDetails.getUsername());
        if (!op.isPresent()) {
            token = this.jwtTokenService.generateToken(userDetails);
            authSession = jwtTokenService.getAuthenticationSession(token);
            AuthenticationSession jwt = new AuthenticationSession(userDetails.getUsername(), authSession, dateService.getCurrentDateInUTC());
            authenticationSessionCrudManagerService.save(jwt);
        } else {
            token = this.jwtTokenService.generateToken(userDetails, op.get().getAuthSession());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("TOKEN " + token);
        return ResponseEntity.ok(new AuthenticationResponse(token, userDTOTransferNew.modelToDTO(userDetails)));
    }

    @Override
    public ResponseEntity<HttpStatus> cleanAuthenticationSessionKey(JwtCleanToken jwtCleanToken) {
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

    @Override
    @Transactional
    public ResponseEntity<AuthenticationResponse> refreshAuthenticationSessionKey(JwtCleanToken jwtCleanToken) {
        Optional<CustomSecurityUser> optional = securitySupportService.getPrincipal();
        if (optional.isPresent()) {
            if (optional.get().getUser().getRole() == UserRoleEnum.ADMIN || optional.get().getUser().getUserAdditionalData().getUsername().equals(jwtCleanToken.getUsername())) {
                authenticationSessionCrudManagerService.delete(jwtCleanToken.getUsername());
                String authSession = UUID.randomUUID().toString();
                AuthenticationSession jwt = new AuthenticationSession(jwtCleanToken.getUsername(), authSession, dateService.getCurrentDateInUTC());
                authenticationSessionCrudManagerService.save(jwt);
                String token = this.jwtTokenService.generateToken(jwtCleanToken.getUsername(), authSession);
                return ResponseEntity.ok(new AuthenticationResponse(token, userDTOTransferNew.modelToDTO(optional.get().getUser())));
            } else {
                return ResponseEntity.status(403).build();
            }
        } else {
            return ResponseEntity.status(204).build();
        }
    }
}
