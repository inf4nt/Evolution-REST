package evolution.data;

import evolution.model.dialog.Dialog;
import evolution.model.message.Message;
import evolution.model.user.UserLight;
import evolution.security.model.CustomSecurityUser;
import evolution.service.MessageTechService;
import evolution.service.SecuritySupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 04.09.2017.
 */
@Service
public class MessageDataService {

    private final MessageRepository messageRepository;

    private final DialogDataService dialogDataService;

    private final SecuritySupportService securitySupportService;

    private final MessageTechService messageTechService;

    @Autowired
    public MessageDataService(MessageRepository messageRepository,
                              DialogDataService dialogDataService, SecuritySupportService securitySupportService, MessageTechService messageTechService) {
        this.messageRepository = messageRepository;
        this.dialogDataService = dialogDataService;
        this.securitySupportService = securitySupportService;
        this.messageTechService = messageTechService;
    }

    /**
     * метод сохраняет сообщения, проверит существует ли диалог, если не существует создаст новый
     *
     * @param text         текс сообщения
     * @param senderUser кто пишет сообщение
     * @param secondUser кому пишет сообщение
     * @return
     */
    public Message save(String text, UserLight senderUser, UserLight secondUser) {
        Optional optional = dialogDataService.findDialogByUsers(senderUser.getId(), secondUser.getId());
        Message message = new Message();
        Dialog dialog;

        if (optional.isPresent()) {
            dialog = (Dialog) optional.get();  // dialog exist
        } else {
            dialog = new Dialog(senderUser, secondUser); // dialog not exist
        }

        message.setMessage(text);
        message.setDialog(dialog);
        message.setDateDispatch(new Date());
        message.setSender(senderUser);

        return this.messageRepository.saveAndFlush(message);
    }

    public Message save(Message message) {
        return save(message.getMessage(), message.getSender(), message.getDialog().getSecond());
    }

    public List<Message> findMessageByUsers(Long userId1, Long userId2, Pageable pageable) {
        return messageRepository.findMessageByUsers(userId1, userId2, pageable);
    }

    public List<Message> findMessageByUsersRepairDialog(Long userId1, Long userId2, Pageable pageable) {
        return messageTechService.repairMessageList(findMessageByUsers(userId1, userId2, pageable));
    }

    public List<Message> findLastMessageForDialog(Long userId) {
        return messageRepository.findLastMessageForDialog(userId);
    }

    public List<Message> findLastMessageForDialogAfterRepairDialog(Long userId) {
        return messageTechService.repairMessageList(findLastMessageForDialog(userId));
    }

    public Optional<Message> findOne(Long messageId, Long senderId) {
        return Optional.ofNullable(messageRepository.findOne(messageId, senderId));
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public List<Message> findAllAfterRepairDialog() {
        return messageTechService.repairMessageList(findAll());
    }

    public Page<Message> findAll(Pageable pageable) {
        return this.messageRepository.findAll(pageable);
    }

    public Optional findOne(Long id) {
        return Optional.ofNullable(messageRepository.findOne(id));
    }

    public List<Message> save(List<Message> messageList) {
        return this.messageRepository.save(messageList);
    }

    public void delete(Message message) {
        this.messageRepository.delete(message);
    }

    public void delete(Long id) {
        Optional optional = findOne(id);
        if (optional.isPresent())
            this.messageRepository.delete((Message) optional.get());
    }

    /**
     * delete the message, but before delete check authorities
     * <p>
     * метод удаляет сообщение. Проверит можно ли его удалить. Проверит по аутентифицикации
     *
     * @param messageId
     */
    public void deleteByAuthentication(Long messageId) {
        Optional<CustomSecurityUser> principal = securitySupportService.getPrincipal();
        if (principal.isPresent()) {
            Optional<Message> message = findOne(messageId, principal.get().getUser().getId());
            message.ifPresent(this::delete);
        }
    }

    public void delete(List<Message> messageList) {
        this.messageRepository.delete(messageList);
    }
}
