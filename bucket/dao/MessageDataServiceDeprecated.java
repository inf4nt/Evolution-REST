package evolution.dao;

import evolution.model.dialog.Dialog;
import evolution.model.message.Message;
import evolution.model.user.UserLight;
import evolution.model.user.User;
import evolution.security.model.CustomSecurityUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Infant on 13.07.2017.
 */
@Service
@Deprecated
public class MessageDataServiceDeprecated {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DialogRepositoryDep dialogRepositoryDep;

    private final MessageRepositoryDeprecated messageRepositoryDeprecated;

    @Autowired
    public MessageDataServiceDeprecated(DialogRepositoryDep dialogRepositoryDep, MessageRepositoryDeprecated messageRepositoryDeprecated) {
        this.dialogRepositoryDep = dialogRepositoryDep;
        this.messageRepositoryDeprecated = messageRepositoryDeprecated;
    }

    public boolean existDialog(Long userId1, Long userId2) {
        return dialogRepositoryDep.checkDialog(userId1, userId2) != null;
    }

    public List<Message> findAll() {
        return this.messageRepositoryDeprecated.findAll();
    }

    /**
     * проверяет существует ли диалог между пользователями, после
     * возвращает сообщения пользователей
     * @param userId1
     * @param userId2
     * @param pageable
     * @return
     */
    public List<Message> findMessage(Long userId1, Long userId2, Pageable pageable) {
        if(existDialog(userId1, userId2)) {
            return messageRepositoryDeprecated.findMessage(userId1, userId2, pageable);
        }
        return new ArrayList<>();
    }

    public List<Message> findMessageIgnoreDialog(Long userId1, Long userId2, Pageable pageable) {
        LOGGER.warn("Message not have dialog, maybe catch null pointer exception");
        return messageRepositoryDeprecated.findMessageIgnoreDialog(userId1, userId2, pageable);
    }

    /**
     * последние сообщения по диалогам
     * @param userId
     * @return
     */
    public List<Message> findLastMessageForDialog(Long userId) {
        List<Message> list = this.messageRepositoryDeprecated.findLastMessageForDialog(userId);
        return reverseDialog(list);
    }

    @Transactional
    public Message saveDialogAndMessage(Dialog dialog, Message message) {
        Dialog d = dialogRepositoryDep.save(dialog);
        message.setDialog(d);
        return messageRepositoryDeprecated.save(message);
    }

    @Transactional
    public Message saveDialogAndMessage(Long senderUserId, Long secondUserId, String message, Date dateMessage) {
        Dialog dialog = new Dialog(new UserLight(senderUserId), new UserLight(secondUserId));
        dialogRepositoryDep.save(dialog);
        Message m = new Message(new UserLight(senderUserId), message, dateMessage);
        m.setDialog(dialog);
        return messageRepositoryDeprecated.save(m);
    }

    public Message save(Message message) {
        return messageRepositoryDeprecated.save(message);
    }

    public Dialog findDialogByFirstAndSecond(@Param("first") Long firstUserId, @Param("second") Long secondUserId) {
        return dialogRepositoryDep.findDialogByFirstAndSecond(firstUserId, secondUserId);
    }

    public Dialog selectDialogIdByFirstAndSecond(@Param("first") Long firstUserId, @Param("second") Long secondUserId) {
        return dialogRepositoryDep.selectDialogIdByFirstAndSecond(firstUserId, secondUserId);
    }

    /**
     * заменяет ссылки юзеров в диалоге. First это тот кто в системе, second с кем общаемся
     * @param list
     * @return
     */
    private List<Message> reverseDialog(List<Message> list) {
        User user = ((CustomSecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        list.forEach(message -> {
            if (!message.getDialog().getFirst().getId().equals(user.getId())) {
                message.getDialog().setSecond(message.getDialog().getFirst());
                message.getDialog().setFirst(new UserLight(user));
            }
        });
        return list;
    }

}
