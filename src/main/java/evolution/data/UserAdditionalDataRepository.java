package evolution.data;

import evolution.model.UserAdditionalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Infant on 29.10.2017.
 */
interface UserAdditionalDataRepository extends JpaRepository<UserAdditionalData, Long> {

    @Query("select u from UserAdditionalData u where u.user.id =:userId")
    UserAdditionalData findByUserId(@Param("userId") Long userId);

    @Query("select u from UserAdditionalData u where u.secretKey =:secretKey")
    UserAdditionalData findBySecretKey(@Param("secretKey") String secretKey);
}
