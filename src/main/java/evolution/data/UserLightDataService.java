package evolution.data;

import evolution.model.user.UserLight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 04.09.2017.
 */
@Service
@Transactional(readOnly = true)
public class UserLightDataService {

    private final UserLightRepository userLightRepository;

    @Autowired
    public UserLightDataService(UserLightRepository userLightRepository) {
        this.userLightRepository = userLightRepository;
    }

    public List<UserLight> findAll() {
        return userLightRepository.findAll();
    }

    public Page<UserLight> findAll(Pageable pageable) {
        return userLightRepository.findAll(pageable);
    }

    public Optional<UserLight> findOne(Long id) {
        return Optional.ofNullable(userLightRepository.findOne(id));
    }
}
