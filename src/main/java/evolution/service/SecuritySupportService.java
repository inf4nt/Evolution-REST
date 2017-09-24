package evolution.service;

import evolution.security.model.CustomSecurityUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Infant on 09.09.2017.
 */
@Service
public class SecuritySupportService {

    public Optional<CustomSecurityUser> getPrincipal() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(object instanceof String) && object instanceof CustomSecurityUser) {
            CustomSecurityUser customSecurityUser = (CustomSecurityUser) object;
            return Optional.of(customSecurityUser);
        }
        return Optional.empty();
    }

}
