package evolution.repository;

import evolution.model.Dialog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 07.11.2017.
 */
public interface DialogRepository extends JpaRepository<Dialog, Long> {

    @Query("select d " +
            "from Dialog d " +
            "where d.id =:dialogId")
    Optional<Dialog> findOneDialog(@Param("dialogId") Long dialogId);

    @Query("select d " +
            "from Dialog d " +
            "where d.id =:dialogId " +
            "and (d.first.id =:iam or d.second.id =:iam)")
    Optional<Dialog> findOneDialog(@Param("dialogId") Long dialogId, @Param("iam") Long iam);

    @Query("select d " +
            "from Dialog d " +
            " where ( d.first.id =:u1 and d.second.id =:u2 ) " +
            " or ( d.first.id =:u2 and d.second.id =:u1 ) ")
    Optional<Dialog> findDialogByUsers(@Param("u1") Long user1, @Param("u2") Long user2);

    @Query(" select d " +
            " from Dialog d " +
            " where d.first.id =:iam or d.second.id =:iam  ")
    Page<Dialog> findMyDialog(@Param("iam") Long iam, Pageable pageable);

    @Query(" select d " +
            " from Dialog d " +
            " where d.first.id =:iam or d.second.id =:iam  ")
    List<Dialog> findMyDialog(@Param("iam") Long iam, Sort sort);

    @Query(" select d " +
            " from Dialog d " +
            " where d.first.id =:iam or d.second.id =:iam  ")
    List<Dialog> findMyDialog(@Param("iam") Long id);
}
