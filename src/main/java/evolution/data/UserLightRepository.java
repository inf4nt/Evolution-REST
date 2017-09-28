package evolution.data;

import evolution.model.user.UserLight;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Infant on 04.09.2017.
 */
interface UserLightRepository extends JpaRepository<UserLight, Long> {
}
