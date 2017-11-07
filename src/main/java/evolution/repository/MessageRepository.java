package evolution.repository;

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
 * Created by Infant on 07.11.2017.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(" select m " +
            " from Message m " +
            " join m.dialog as d " +
            " where d.id =:dialogId " +
            " and (d.first.id =:userId or d.second.id =userId) ")
    Page<Message> findMessageByDialogAndUserDialog(@Param("dialogId") Long dialogId, @Param("userId") Long userId);

    @Query(" select m " +
            " from Message m " +
            " join m.dialog as d " +
            " where d.id =:dialogId ")
    Page<Message> findMessageByDialog(@Param("dialogId") Long dialogId);

    @Query("select m from Message m where m.id =:id")
    Optional<Message> findOneMessageById(@Param("id") Long messageId);

    @Query("select m from Message m where m.id =:id and m.sender.id =:senderId")
    Optional<Message> findOneMessageByMessageIdAndSenderId(@Param("id") Long messageId, @Param("senderId") Long senderId);

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
    Page<Message> findLastMessageInMyDialog(@Param("userId") Long iam, Pageable pageable);

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
    List<Message> findLastMessageInMyDialog(@Param("userId") Long iam, Sort sort);

    @Query("select m from Message m where m.sender.id =:senderId")
    Page<Message> findMessageBySenderId(@Param("senderId") Long senderId, Pageable pageable);

    @Query("select m from Message m where m.sender.id =:senderId")
    List<Message> findMessageBySenderId(@Param("senderId") Long senderId, Sort sort);

    @Query(" select m from Message m " +
            " join m.dialog as d " +
            " where m.sender.id <> recipientId " +
            " and (d.first.id =:recipientId or d.second.id =:recipientId) ")
    Page<Message> findMessageByRecipientId(@Param("recipientId") Long recipientId, Pageable pageable);

    @Query(" select m from Message m " +
            " join m.dialog as d " +
            " where m.sender.id <> recipientId " +
            " and (d.first.id =:recipientId or d.second.id =:recipientId) ")
    List<Message> findMessageByRecipientId(@Param("recipientId") Long recipientId, Sort sort);
}
