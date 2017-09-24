package evolution.security.service;


import evolution.data.UserDataService;
import evolution.model.user.User;
import evolution.security.model.CustomSecurityUser;
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

import javax.persistence.NoResultException;
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

    private final UserDataService userDataService;

    @Autowired
    public UserDetailsServiceImpl(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional optional = userDataService.findUserByUsername(username);
        LOGGER.info(" loadUserByUsername " + optional);
        if (!optional.isPresent()) {
            LOGGER.info("\nuser  username = " + username + ", not found");
            throw new UsernameNotFoundException("user " + username + " not found");
        }
        User user = (User) optional.get();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return new CustomSecurityUser(
                user.getUsername(),
                user.getPassword(),
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
