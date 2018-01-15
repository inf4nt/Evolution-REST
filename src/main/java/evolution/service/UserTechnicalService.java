package evolution.service;

import evolution.module.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by Infant on 09.10.2017.
 */
@Service
public class UserTechnicalService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserTechnicalService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User encodePassword(User user) {
        String code = encodePassword(user.getUserAdditionalData().getPassword());
        user.getUserAdditionalData().setPassword(code);
        return user;
    }

    public boolean matches(String p1, String encodePassword) {
        BCryptPasswordEncoder enc = (BCryptPasswordEncoder) passwordEncoder;
        return enc.matches(p1, encodePassword);
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
