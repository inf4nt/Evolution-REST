//package evolution.data;
//
//import evolution.model.Dialog;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//
///**
// * Created by Infant on 04.09.2017.
// */
//interface DialogRepository extends JpaRepository<Dialog, Long> {
//
//    @Query("select d from Dialog d " +
//            " join fetch d.first as f " +
//            " join fetch d.second as s" +
//            " where (d.first.id =:id1 and d.second.id =:id2) " +
//            " or (d.first.id =:id2 and d.second.id =:id1)")
//    Dialog findDialogByUsers(@Param("id1") Long id1, @Param("id2") Long id2);
//
//    @Query(" select d " +
//            " from Dialog d" +
//            " where d.first.id =:userId " +
//            " or d.second.id =:userId ")
//    List<Dialog> findDialogByUser(@Param("userId") Long userId);
//}
