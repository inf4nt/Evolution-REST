package evolution.data;

import evolution.model.message.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by Infant on 04.09.2017.
 */
interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(" select m from Message m " +
            " join m.dialog as d " +
            " join m.sender as sender " +
            " where (d.first.id =:id1 and d.second.id =:id2 ) " +
            " or (d.first.id =:id2 and d.second.id =:id1 ) order by m.id desc ")
    List<Message> findMessageByUsers(@Param("id1") Long id1, @Param("id2") Long id2, Pageable pageable);

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
    List<Message> findLastMessageForDialog(@Param("id1") Long authUserId);

    @Query("select m " +
            "from Message m " +
            "where m.id =:messageId and m.sender.id =:senderId")
    Message findOne(@Param("messageId") Long messageId, @Param("senderId") Long senderId);


    @Query(" select m from Message m " +
            " join fetch m.dialog as d " +
            " where d.id =:dialogId " +
            " and (d.first.id =:userId " +
            " or d.second.id =:userId )")
    List<Message> findMessageByDialogIdAndSomeDialogUserId(@Param("dialogId") Long dialogId, @Param("userId") Long userId, Pageable pageable);

    @Query(" select m from Message m " +
            " join fetch m.dialog as d " +
            " where d.id =:dialogId " +
            " and (d.first.id =:userId " +
            " or d.second.id =:userId )")
    List<Message> findMessageByDialogIdAndSomeDialogUserId(@Param("dialogId") Long dialogId, @Param("userId") Long userId);

    @Query("select m from Message m " +
            " join fetch m.dialog as d" +
            " where d.id =:dialogId ")
    List<Message> findMessageByDialogId(@Param("dialogId") Long dialogId);

    @Query("select m from Message m " +
            " join fetch m.dialog as d" +
            " where d.id =:dialogId ")
    List<Message> findMessageByDialogId(@Param("dialogId") Long dialogId, Pageable pageable);
}
