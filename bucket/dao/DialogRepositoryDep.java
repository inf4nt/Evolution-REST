package evolution.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


interface DialogRepositoryDep extends JpaRepository<Dialog, Long> {

    @Query("select 1 from Dialog d " +
            " where (d.first.id =:id1 and d.second.id =:id2) " +
            " or (d.first.id =:id2 and d.second.id =:id1)")
    Integer checkDialog(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query("select d " +
//            " new Dialog(d.id, f.id, f.firstName, f.lastName, s.id, s.firstName, s.lastName) " +
            " from Dialog d " +
            " join d.first as f " +
            " join d.second as s" +
            " where d.first.id = :first and d.second.id = :second " +
            " or d.first.id = :second and d.second.id = :first ")
    Dialog findDialogByFirstAndSecond(@Param("first") Long firstUserId, @Param("second") Long secondUserId);

    @Query(" select d " +
//            "  new Dialog(d.id)
            " from Dialog d " +
            " where d.first.id = :first and d.second.id = :second " +
            " or d.first.id = :second and d.second.id = :first  ")
    Dialog selectDialogIdByFirstAndSecond(@Param("first") Long firstUserId, @Param("second") Long secondUserId);
}
