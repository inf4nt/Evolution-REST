package evolution.data;

import evolution.model.Message;
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
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(" select m from Message m " +
            " join m.dialog as d " +
            " join m.sender as sender " +
            " where (d.first.id =:id1 and d.second.id =:id2 ) " +
            " or (d.first.id =:id2 and d.second.id =:id1 ) order by m.id desc ")
    List<Message> findMessageByUsers(@Param("id1") Long id1, @Param("id2") Long id2, Pageable pageable);

    @Query("select m from Message m where m.id =:id")
    Optional<Message> findOneMessage(@Param("id") Long id);

    @Query("select m from Message m where m.id =:id and m.sender.id =:senderId")
    Optional<Message> findOneMessage(@Param("id") Long id, Long senderId);

    @Query(" select m from Message m " +
            " join m.dialog as d " +
            " join m.sender as sender " +
            " where (d.first.id =:id1 and d.second.id =:id2 ) " +
            " or (d.first.id =:id2 and d.second.id =:id1 ) order by m.id desc ")
    List<Message> findMessageByUsers(@Param("id1") Long id1, @Param("id2") Long id2);

    @Query(" select m " +
            " from Message m " +
            " where m.id in ( " +
            " select max (m.id) " +
            " from Message m " +
            " join m.dialog as d " +
            " where d.first.id =:id1 " +
            " or d.second.id =:id1 " +
            " group by m.dialog.id ) " +
            " order by m.id desc ")
    List<Message> findLastUserMessageInDialogWhereUserId(@Param("id1") Long authUserId);

    @Deprecated
    @Query(" select m " +
            " from Message m " +
            " where m.id in ( " +
            " select max (m.id) " +
            " from Message m " +
            " join m.dialog as d " +
            " where d.first.id =:id1 " +
            " or d.second.id =:id1 " +
            " group by m.dialog.id ) " +
            " order by m.id desc ")
    Page<Message> findLastUserMessageInDialogWhereUserId(@Param("id1") Long authUserId, Pageable pageable);

    @Query(" select m " +
            " from Message m " +
            " where m.id in ( " +
            " select max (m.id) " +
            " from Message m " +
            " join m.dialog as d " +
            " where d.first.id =:id1 " +
            " or d.second.id =:id1 " +
            " group by m.dialog.id ) " +
            " order by m.id desc ")
    Page<Message> findLastMessageInMyDialogs(@Param("id1") Long authUserId, Pageable pageable);

    @Query(" select m " +
            " from Message m " +
            " where m.id in ( " +
            " select max (m.id) " +
            " from Message m " +
            " join m.dialog as d " +
            " where d.first.id =:id1 " +
            " or d.second.id =:id1 " +
            " group by m.dialog.id ) " +
            " order by m.id desc ")
    List<Message> findLastUserMessageInDialogWhereUserId(@Param("id1") Long authUserId, Sort sort);

    @Query(" select m " +
            " from Message m " +
            " where m.id =:messageId and m.sender.id =:senderId")
    Message findOne(@Param("messageId") Long messageId, @Param("senderId") Long senderId);

    @Query("select m from Message m " +
            " where m.dialog.id =:dialogId ")
    Page<Message> findMessageByDialog(@Param("dialogId") Long dialogId, Pageable pageable);

    @Query(" select m from Message m " +
            " where (m.dialog.first.id =:user1 and m.dialog.second.id =:user2) " +
            " or (m.dialog.first.id =:user2 and m.dialog.second.id =:user1) ")
    Page<Message> findMessageByDialogUsers(@Param("user1") Long user1, @Param("user2") Long user2, Pageable pageable);
}
