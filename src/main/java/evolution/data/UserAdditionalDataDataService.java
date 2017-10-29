package evolution.data;

import evolution.model.UserAdditionalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * Created by Infant on 29.10.2017.
 */
@Service
public class UserAdditionalDataDataService {

    private final UserAdditionalDataRepository userAdditionalDataRepository;

    @Autowired
    public UserAdditionalDataDataService(UserAdditionalDataRepository userAdditionalDataRepository) {
        this.userAdditionalDataRepository = userAdditionalDataRepository;
    }

    @Transactional(readOnly = true)
    public Optional<UserAdditionalData> findByUserId(Long userId){
        return Optional.ofNullable(userAdditionalDataRepository.findByUserId(userId));
    }

    @Transactional(readOnly = true)
    public Optional<UserAdditionalData> findBySecretKey(String secretKey) {
        return Optional.ofNullable(userAdditionalDataRepository.findBySecretKey(secretKey));
    }
}
