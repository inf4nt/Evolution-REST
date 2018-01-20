package evolution.security;

import evolution.model.AuthenticationSession;
import evolution.security.service.UserAuthenticationService;
import evolution.security.token.JwtTokenService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.setHeader("Access-Control-Expose-Headers", "Row-Count");

        System.out.println("Auth filter ============================================================");
        String authToken = request.getHeader("Authorization");

        System.out.println("doFilterInternal authToken " + authToken);

        if (authToken != null && authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7);
        }

////        todo: remove after testing
//        String username = "com.infant@gmail.com";
//
//        if (SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//
//            UsernamePasswordAuthenticationToken authentication =
//                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        }


        String username = jwtTokenService.getUsername(authToken);

        logger.info("checking authentication für user " + username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            CompletableFuture<Optional<UserDetails>> cfu = userAuthenticationService.loadByUsernameAsync(username);
            CompletableFuture<Optional<AuthenticationSession>> cfa = userAuthenticationService
                    .findAuthenticationSessionBySessionKeyAsync(jwtTokenService.getAuthenticationSession(authToken));

            CompletableFuture.allOf(cfu, cfa);

            Optional<UserDetails> userDetails = cfu.join();
            Optional<AuthenticationSession> authenticationSession = cfa.join();

            if (userDetails.isPresent() && authenticationSession.isPresent()) {
                if (this.jwtTokenService.isValidToken(authToken, userDetails.get())) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails.get(), null, userDetails.get().getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }

        System.out.println("Auth filter ============================================================");
        chain.doFilter(request, response);
    }
}