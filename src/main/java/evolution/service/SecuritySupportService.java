package evolution.service;

import evolution.common.ServiceStatus;
import evolution.common.UserRoleEnum;
import evolution.data.UserDataService;
import evolution.exception.AuthenticationPrincipalNotFoundException;
import evolution.model.User;
import evolution.security.model.CustomSecurityUser;
import evolution.security.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Infant on 09.09.2017.
 */
@Service
public class SecuritySupportService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Deprecated
    public Optional<CustomSecurityUser> getPrincipal() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(object instanceof String) && object instanceof CustomSecurityUser) {
            CustomSecurityUser customSecurityUser = (CustomSecurityUser) object;
            return Optional.of(customSecurityUser);
        } else {
            LOGGER.info("fail instanceof for object = " + object);
        }
        return Optional.empty();
    }

    public CustomSecurityUser getAuthenticationPrincipal() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(object instanceof String) && object instanceof CustomSecurityUser) {
            return (CustomSecurityUser) object;
        } else {
            throw new AuthenticationPrincipalNotFoundException("not found authentication in security context");
        }
    }

    public ServiceStatus isAllowed(Long id) {
        Optional<CustomSecurityUser> principal = getPrincipal();

        if (!principal.isPresent()) {
            LOGGER.warn("principal is null");
            return ServiceStatus.EXPECTATION_FAILED;
        }

        User user = principal.get().getUser();
        LOGGER.info("principal is = " + user);

        //todo: fix
//        if (user.getRoleId().equals(UserRoleEnum.ADMIN.getId()) || user.getId().equals(id))
//            return ServiceStatus.TRUE;

        return ServiceStatus.FALSE;
    }

}
