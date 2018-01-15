package evolution.module.security.service;


import evolution.module.security.dto.CustomSecurityUser;
import evolution.module.user.crud.api.UserCrudManagerService;
import evolution.module.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 01.03.2017.
 */
@Service
public class UserDetailsServiceImpl
        implements UserDetailsService {

    private Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserCrudManagerService userCrudManagerService;

    @Autowired
    public UserDetailsServiceImpl(UserCrudManagerService userCrudManagerService) {
        this.userCrudManagerService = userCrudManagerService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = userCrudManagerService.findByUsernameLazy(username);
        if (!optional.isPresent()) {
            LOGGER.info("\nuser  username = " + username + ", not found");
            throw new UsernameNotFoundException("user " + username + " not found");
        }
        User user = optional.get();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return new CustomSecurityUser(
                user.getUserAdditionalData().getUsername(),
                user.getUserAdditionalData().getPassword(),
                true,
                true,
                true,
                true,
                grantedAuthorities,
                user,
                null
        );
    }

}
