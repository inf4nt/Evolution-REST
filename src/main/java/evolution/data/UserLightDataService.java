package evolution.data;

import evolution.model.user.UserLight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Infant on 04.09.2017.
 */
@Service
public class UserLightDataService {

    private final UserLightRepository userLightRepository;

    @Autowired
    public UserLightDataService(UserLightRepository userLightRepository) {
        this.userLightRepository = userLightRepository;
    }

    public List<UserLight> findAll() {
        return this.userLightRepository.findAll();
    }

    public Page<UserLight> findAll(Pageable pageable) {
        return this.userLightRepository.findAll(pageable);
    }

    public UserLight findOne(Long id) {
        return this.userLightRepository.findOne(id);
    }
}
