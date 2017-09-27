package evolution.data;

import evolution.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 04.09.2017.
 */
@Service
public class UserDataService {

    private final UserRepository userRepository;

    @Autowired
    public UserDataService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(this.userRepository.findUserByUsername(username));
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public Page<User> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    public Optional<User> findOne(Long id) {
        return Optional.ofNullable(this.userRepository.findOne(id));
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public List<User> save(List<User> userList) {
        return this.userRepository.save(userList);
    }

    public void delete(User user) {
        this.userRepository.delete(user);
    }

    public void delete(Long id) {
        this.userRepository.delete(id);
    }

    public void delete(List<User> userList) {
        this.userRepository.delete(userList);
    }
}
