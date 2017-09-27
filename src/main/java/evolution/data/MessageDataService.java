package evolution.data;

import evolution.model.dialog.Dialog;
import evolution.model.message.Message;
import evolution.model.user.UserLight;
import evolution.security.model.CustomSecurityUser;
import evolution.service.SecuritySupportService;
import evolution.service.TechnicalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final TechnicalDataService technicalDataService;

    @Autowired
    public MessageDataService(MessageRepository messageRepository,
                              DialogDataService dialogDataService, SecuritySupportService securitySupportService, TechnicalDataService technicalDataService) {
        this.messageRepository = messageRepository;
        this.dialogDataService = dialogDataService;
        this.securitySupportService = securitySupportService;
        this.technicalDataService = technicalDataService;
    }

    /**
     * метод сохраняет сообщения, проверит существует ли диалог, если не существует создаст новый
     *
     * @param text       текс сообщения
     * @param senderUser кто пишет сообщение
     * @param secondUser кому пишет сообщение
     * @return
     */
    @Transactional
    public Message save(String text, UserLight senderUser, UserLight secondUser) {
        Optional<Dialog> optional = dialogDataService.findDialogByUsers(senderUser.getId(), secondUser.getId());
        Message message = new Message();
        Dialog dialog;

        if (optional.isPresent()) {
            dialog = optional.get();  // dialog exist
        } else {
            dialog = new Dialog(senderUser, secondUser); // dialog not exist
        }

        message.setMessage(text);
        message.setDialog(dialog);
        message.setDateDispatch(new Date());
        message.setSender(senderUser);

        return this.messageRepository.saveAndFlush(message);
    }

    @Transactional
    public Message save(Message message) {
        return save(message.getMessage(), message.getSender(), message.getDialog().getSecond());
    }

    @Transactional(readOnly = true)
    public List<Message> findMessageByUsers(Long userId1, Long userId2, Pageable pageable) {
        return messageRepository.findMessageByUsers(userId1, userId2, pageable);
    }

    @Transactional(readOnly = true)
    public List<Message> findMessageByUsersRepairDialog(Long userId1, Long userId2, Pageable pageable) {
        List<Message> list = messageRepository.findMessageByUsers(userId1, userId2, pageable);
        return technicalDataService.repairDialogForMessageList(list);
    }

    @Transactional(readOnly = true)
    public List<Message> findLastMessageForDialogByUser(Long userId) {
        return messageRepository.findLastMessageForDialog(userId);
    }

    @Transactional(readOnly = true)
    public List<Message> findLastMessageForDialogAfterRepairDialogByUser(Long userId) {
        List<Message> list = findLastMessageForDialogByUser(userId);
        return technicalDataService.repairDialogForMessageList(list);
    }

    @Transactional(readOnly = true)
    public Optional<Message> findOne(Long messageId, Long senderId) {
        return Optional.ofNullable(messageRepository.findOne(messageId, senderId));
    }

    @Transactional(readOnly = true)
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Message> findAllAfterRepairDialog() {
        List<Message> list = messageRepository.findAll();
        return technicalDataService.repairDialogForMessageList(list);
    }

    @Transactional(readOnly = true)
    public Page<Message> findAll(Pageable pageable) {
        return this.messageRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Message> findOne(Long id) {
        return Optional.ofNullable(messageRepository.findOne(id));
    }

    @Transactional(readOnly = true)
    public Optional<Message> findOneRepairDialog(Long id) {
        Message message = messageRepository.findOne(id);
        if(message == null) {
            return Optional.empty();
        } else {
            return Optional.of(technicalDataService.repairDialog(message));
        }
    }

    @Transactional
    public List<Message> save(List<Message> messageList) {
        return this.messageRepository.save(messageList);
    }

    @Transactional
    public void delete(Message message) {
        dialogDataService.deleteMessageByDialog(message);
    }

    @Transactional
    public void delete(Long id) {
        dialogDataService.deleteMessageByDialog(new Message(id));
    }

    /**
     * delete the message, but before delete check authorities
     * <p>
     * метод удаляет сообщение. Проверит можно ли его удалить. Проверит по аутентифицикации
     *
     * @param messageId
     */
    @Transactional
    public void deleteByAuthentication(Long messageId) {
        Optional<CustomSecurityUser> principal = securitySupportService.getPrincipal();
        if (principal.isPresent()) {
            Optional<Message> message = findOne(messageId, principal.get().getUser().getId());
            message.ifPresent(this::delete);
        }
    }

    @Transactional
    public void delete(List<Message> messageList) {
        this.messageRepository.delete(messageList);
    }

    @Transactional
    public void deleteListMessageById(List<Long> messageListIds) {
        messageListIds
                .forEach(id -> dialogDataService.deleteMessageByDialog(new Message(id)));
    }
}
