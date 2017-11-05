package evolution.data;

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
 * Created by Infant on 22.10.2017.
 */
interface DialogRepository extends JpaRepository<Dialog, Long> {

    @Query("select d from Dialog d " +
            " where (d.first.id = :u1 and d.second.id =:u2) " +
            " or (d.first.id = :u2 and d.second.id =:u1) ")
    Page<Dialog> findDialogWhereUsers(@Param("u1") Long user1, @Param("u2") Long user2, Pageable pageable);

    @Query("select d from Dialog d " +
            " where (d.first.id = :u1 and d.second.id =:u2) " +
            " or (d.first.id = :u2 and d.second.id =:u1) ")
    List<Dialog> findDialogWhereUsers(@Param("u1") Long user1, @Param("u2") Long user2, Sort sort);

    @Query(" select d " +
            " from Dialog d " +
            " where d.second.id =:userid or d.first.id =:userid")
    Page<Dialog> findAllDialogByUserLoadLazy(@Param("userid") Long userId, Pageable pageable);

    @Query("select d from Dialog d where d.id =:dialogId and (d.first.id =:someUserId or d.second.id =:someUserId)")
    Optional<Dialog> findDialogByIdAndSomeUser(@Param("dialogId") Long dialogId, @Param("someUserId") Long someUserId);

    @Query(" select d " +
            " from Dialog d " +
            " join d.first as f" +
            " join d.second as s" +
            " where f.id =:userid or s.id =:userid")
    Page<Dialog> findAllDialogByUser(@Param("userid") Long userId, Pageable pageable);
}
