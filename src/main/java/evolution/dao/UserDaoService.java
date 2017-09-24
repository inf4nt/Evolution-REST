package evolution.dao;

import evolution.common.UserRoleEnum;
import evolution.model.user.UserLight;
import evolution.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Infant on 14.07.2017.
 */
@Service
public class UserDaoService {

    @Autowired
    private StandardUserRepository standardUserRepository;

    @Autowired
    private UserRepositoryDeprecated userRepositoryDeprecated;

    @Transactional
    public UserLight selectIdFirstLastNameStandardUser(Long id) {
        return standardUserRepository.selectIdFirstLastName(id);
    }

    @Transactional
    public List<UserLight> findStandardUserByFirstLastName(String p1, String p2, Pageable pageable) {
        return standardUserRepository.findUserByFirstLastName(p1, p2, pageable);
    }

    @Transactional
    public List<UserLight> findStandardUserByFirstOrLastName(String p1, Pageable pageable) {
        return standardUserRepository.findUserByFirstOrLastName(p1, pageable);
    }

    @Transactional
    public List<UserLight> findStandardUsers(Pageable pageable) {
        return standardUserRepository.findUsers(pageable);
    }

    @Transactional
    public User findUserByUsername(String username) {
//        return userRepositoryDeprecated.findUserByLogin(username);
        return null;
    }

//    @Transactional
//    public List<User> findUserByRoleId(Long roleId) {
//        return userRepositoryDeprecated.findAllByRole(roleId);
//    }
//
//    @Transactional
//    public List<User> findUserByRoleName(String roleName) {
//        return userRepositoryDeprecated.findAllByRole(UserRoleEnum.valueOf(roleName).getId());
//    }

//    @Transactional
//    public List<UserLight> findAll() {
//        return standardUserRepository.findAll();
//    }

//    public List<User> findAllUser() {
//        return this.userRepositoryDeprecated.findAll();
//    }
//
//    @Transactional
//    public User save(User user) {
//        return userRepositoryDeprecated.save(user);
//    }
//
//    @Transactional
//    public void delete(User user) {
//        userRepositoryDeprecated.delete(user);
//    }
//
//    @Transactional
//    public User findOne(Long id) {
//        return userRepositoryDeprecated.findOne(id);
//    }
}
