package evolution.service.validation;

import evolution.common.UserRoleEnum;
import evolution.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Admin on 25.03.2017.
 */
@Service
public class Validator {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final int tweetLength = 10000;

    public boolean userValidator(User user){

        String emailPattern = "^[a-zA-Z0-9._-]{1,40}@[a-zA-Z0-9.-]{1,40}\\.[a-zA-Z]{2,12}";
        String onlyCharacter = "^[a-zA-Z0-9]{4,32}";
        String namePattern = "^[a-zA-Z]{4,32}";

        try {
            if (!user.getUsername().matches(emailPattern)) {
                LOGGER.info("user login is not valid\nlogin = " + user.getUsername());
                return false;
            } else if (!user.getPassword().matches(onlyCharacter)) {
                LOGGER.info("user password is not valid\n password = " + user.getPassword());
                return false;
            } else if (!user.getFirstName().matches(namePattern)) {
                LOGGER.info("user first name is not valid\n first name" + user.getFirstName());
                return false;
            } else if (!user.getLastName().matches(namePattern)) {
                LOGGER.info("user last name is not valid\n last name" + user.getLastName());
                return false;
            } else if (user.getRegistrationDate() == null) {
                LOGGER.info("user registration date is not valid\n registration date = " + user.getRegistrationDate());
                return false;
            } else if (!user.getRoleId().equals(UserRoleEnum.ADMIN.getId()) && !user.getRoleId().equals(UserRoleEnum.USER.getId())) {
                LOGGER.info("user role id is not valid\n role id =" + user.getRoleId());
                return false;
            }
        } catch (Exception e){
            return false;
        }


        LOGGER.info("user valid\n" + user.toString());
        return true;
    }

}
