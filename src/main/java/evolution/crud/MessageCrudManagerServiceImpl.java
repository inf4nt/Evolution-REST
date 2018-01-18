package evolution.crud;

import evolution.crud.api.DialogCrudManagerService;
import evolution.crud.api.MessageCrudManagerService;
import evolution.crud.api.UserCrudManagerService;
import evolution.dto.model.MessageSaveDTO;
import evolution.model.Dialog;
import evolution.model.Message;
import evolution.model.User;
import evolution.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Infant on 07.11.2017.
 */
@Service
public class MessageCrudManagerServiceImpl implements MessageCrudManagerService {

    private final MessageRepository messageRepository;

    private final DialogCrudManagerService dialogCrudManagerService;

    private final UserCrudManagerService userCrudManagerService;

    @Value("${model.message.maxfetch}")
    private Integer messageMaxFetch;

    @Value("${model.message.defaultsort}")
    private String defaultMessageSortType;

    @Value("${model.message.defaultsortproperties}")
    private String defaultMessageSortProperties;

    @Autowired
    public MessageCrudManagerServiceImpl(MessageRepository messageRepository,
                                         DialogCrudManagerService dialogCrudManagerService,
                                         UserCrudManagerService userCrudManagerService) {
        this.messageRepository = messageRepository;
        this.dialogCrudManagerService = dialogCrudManagerService;
        this.userCrudManagerService = userCrudManagerService;
    }


    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public List<Message> findAll(String sort, List<String> sortProperties) {
        Sort s = getSortForRestService(sort, sortProperties,
                this.defaultMessageSortType, this.defaultMessageSortProperties);
        return messageRepository.findAll(s);
    }

    @Override
    public Page<Message> findAll(Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sort, sortProperties,
                this.messageMaxFetch, this.defaultMessageSortType, this.defaultMessageSortProperties);
        return messageRepository.findAll(p);
    }

    @Override
    public Optional<Message> findOne(Long aLong) {
        return messageRepository.findOneMessageById(aLong);
    }

    @Override
    public Message save(Message object) {
        return messageRepository.save(object);
    }

    @Override
    @Transactional
    public Message saveMessageAndMaybeCreateNewDialog(String text, Long senderId, Long recipientId, Date createDateUTC) {
        Dialog dialog;
        Message message = new Message();

//        CompletableFuture<Dialog> cd = dialogRepository.findDialogByUsersAsync(senderId, recipientId);
//        CompletableFuture<User> cr = userRepository.findOneUserByIdAsync(recipientId);
//        CompletableFuture<User> cs = userRepository.findOneUserByIdAsync(senderId);
//
//
//        CompletableFuture.allOf(cd, cr, cs);
//
//        Optional<Dialog> od = Optional.ofNullable(cd.join());
//        Optional<User> or = Optional.ofNullable(cr.join());
//        Optional<User> os = Optional.ofNullable(cs.join());

        CompletableFuture<Optional<Dialog>> cd = dialogCrudManagerService.findDialogByUsersAsync(senderId, recipientId);
        CompletableFuture<Optional<User>> cr = userCrudManagerService.findOneAsync(recipientId);
        CompletableFuture<Optional<User>> cs = userCrudManagerService.findOneAsync(senderId);

        CompletableFuture.allOf(cd, cr, cs);

        Optional<Dialog> od = cd.join();
        Optional<User> or = cr.join();
        Optional<User> os = cs.join();

        os.ifPresent(o -> message.setSender(o));

        if (od.isPresent()) {
            //dialog exist
            dialog = od.get();
        } else {
            // dialog not exist
            dialog = new Dialog();
            dialog.setCreateDate(createDateUTC);


            if (os.isPresent() && or.isPresent()) {
                dialog.setFirst(os.get());
                dialog.setSecond(or.get());
                message.setSender(os.get());
            }
            dialog = dialogCrudManagerService.save(dialog);
        }

        message.setDateDispatch(createDateUTC);
        message.setDialog(dialog);
        message.setMessage(text);
        message.setActive(true);

        return messageRepository.save(message);
    }

    @Override
    public Message saveMessageAndMaybeCreateNewDialog(MessageSaveDTO messageSaveDTO, Date createUTC) {
        return saveMessageAndMaybeCreateNewDialog(messageSaveDTO.getText(), messageSaveDTO.getSenderId(), messageSaveDTO.getRecipientId(), createUTC);
    }

    @Override
    public List<Message> findMessageByInterlocutor(Long interlocutor, Long second) {
        return messageRepository.findMessageByInterlocutor(interlocutor, second);
    }

    @Override
    public List<Message> findMessageByInterlocutor(Long interlocutor, Long second, String sortType, List<String> sortProperties) {
        Sort s = getSortForRestService(sortType, sortProperties,
                this.defaultMessageSortType, this.defaultMessageSortProperties);
        return messageRepository.findMessageByInterlocutor(interlocutor, second, s);
    }

    @Override
    public Page<Message> findMessageByInterlocutor(Long interlocutor, Long second, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sort, sortProperties,
                this.messageMaxFetch, this.defaultMessageSortType, this.defaultMessageSortProperties);
        return messageRepository.findMessageByInterlocutor(interlocutor, second, p);
    }

    @Override
    public void delete(Long aLong) {
        Optional<Message> message = messageRepository.findOneMessageById(aLong);
        message.ifPresent(messageRepository::delete);
    }

    @Override
    @Transactional
    public void deleteMessageAndMaybeDialog(Long messageId) {
        Optional<Message> message = messageRepository.findOneMessageById(messageId);
        message.ifPresent(o -> {
            Dialog dialog = message.get().getDialog();
            List<Message> messageList = dialog.getMessageList();
            if (!messageList.isEmpty()) {
                if (messageList.size() == 1) {
                    dialogCrudManagerService.delete(dialog);
                } else {
                    messageList.remove(message.get());
                    dialogCrudManagerService.save(dialog);
                }
            }
        });
    }

    @Override
    @Transactional
    public void deleteMessageAndMaybeDialog(Long messageId, Long senderId) {
        Optional<Message> message = messageRepository.findOneByMessageIdAndSender(messageId, senderId);
        message.ifPresent(o -> {
            Dialog dialog = message.get().getDialog();
            List<Message> messageList = dialog.getMessageList();
            if (!messageList.isEmpty()) {
                if (messageList.size() == 1) {
                    dialogCrudManagerService.delete(dialog);
                } else {
                    messageList.remove(message.get());
                    dialogCrudManagerService.save(dialog);
                }
            }
        });
    }

    @Override
    @Transactional
    public void deleteMessageAndMaybeDialog(List<Long> ids) {
        ids.forEach(o -> deleteMessageAndMaybeDialog(o));
    }

    @Override
    @Transactional
    public void deleteMessageAndMaybeDialog(List<Long> ids, Long senderId) {
        ids.forEach(o -> deleteMessageAndMaybeDialog(o, senderId));
    }

    @Override
    public Optional<Message> findOne(Long messageId, Long senderId) {
        return messageRepository.findOneByMessageIdAndSender(messageId, senderId);
    }

    @Override
    public List<Message> findMessageByDialogId(Long dialogId) {
        return messageRepository.findMessageByDialog(dialogId);
    }

    @Override
    public List<Message> findMessageByDialogId(Long dialogId, String sort, List<String> sortProperties) {
        Sort s = getSortForRestService(sort, sortProperties,
                this.defaultMessageSortType, this.defaultMessageSortProperties);
        return messageRepository.findMessageByDialog(dialogId, s);
    }

    @Override
    public Page<Message> findMessageByDialogId(Long dialogId, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sort, sortProperties,
                this.messageMaxFetch, this.defaultMessageSortType, this.defaultMessageSortProperties);
        return messageRepository.findMessageByDialog(dialogId, p);
    }

    @Override
    public Page<Message> findMessageByDialogId(Long dialogId, Long iam, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sort, sortProperties,
                this.messageMaxFetch, this.defaultMessageSortType, this.defaultMessageSortProperties);
        return messageRepository.findMessageByDialogIdAndParticipant(dialogId, iam, p);
    }

    @Override
    public List<Message> findMessageByDialogIdAndParticipant(Long dialogId, Long participantId) {
        return messageRepository.findMessageByDialogIdAndParticipant(dialogId, participantId);
    }

    @Override
    public List<Message> findMessageByDialogIdAndParticipant(Long dialogId, Long participantId, String sort, List<String> sortProperties) {
        Sort s = getSortForRestService(sort, sortProperties,
                this.defaultMessageSortType, this.defaultMessageSortProperties);
        return messageRepository.findMessageByDialogIdAndParticipant(dialogId, participantId, s);
    }

    @Override
    public Page<Message> findMessageByDialogIdAndParticipant(Long dialogId, Long participantId, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sort, sortProperties,
                this.messageMaxFetch, this.defaultMessageSortType, this.defaultMessageSortProperties);
        return messageRepository.findMessageByDialogIdAndParticipant(dialogId, participantId, p);
    }

    @Override
    public List<Message> findMessageByDialogId(Long dialogId, Long iam, String sort, List<String> sortProperties) {
        Sort s = getSortForRestService(sort, sortProperties,
                this.defaultMessageSortType, this.defaultMessageSortProperties);
        return messageRepository.findMessageByDialogIdAndParticipant(dialogId, iam, s);
    }

    @Override
    public List<Message> findMessageByDialogId(Long dialogId, Long iam) {
        return messageRepository.findMessageByDialogIdAndParticipant(dialogId, iam);
    }

    @Override
    public Page<Message> findLastMessageInMyDialog(Long iam, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sort, sortProperties,
                this.messageMaxFetch, this.defaultMessageSortType, this.defaultMessageSortProperties);
        return messageRepository.findLastMessageInMyDialog(iam, p);
    }

    @Override
    public List<Message> findLastMessageInMyDialog(Long iam) {
        return messageRepository.findLastMessageInMyDialog(iam);
    }

    @Override
    public List<Message> findLastMessageInMyDialog(Long iam, String sort, List<String> sortProperties) {
        Sort s = getSortForRestService(sort, sortProperties,
                this.defaultMessageSortType, this.defaultMessageSortProperties);
        return messageRepository.findLastMessageInMyDialog(iam, s);
    }

    @Override
    public Page<Message> findMessageBySenderId(Long senderId, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sort, sortProperties,
                this.messageMaxFetch, this.defaultMessageSortType, this.defaultMessageSortProperties);
        return messageRepository.findMessageBySenderId(senderId, p);
    }

    @Override
    public List<Message> findMessageBySenderId(Long senderId) {
        return messageRepository.findMessageBySenderId(senderId);
    }

    @Override
    public List<Message> findMessageBySenderId(Long senderId, String sort, List<String> sortProperties) {
        Sort s = getSortForRestService(sort, sortProperties,
                this.defaultMessageSortType, this.defaultMessageSortProperties);
        return messageRepository.findMessageBySenderId(senderId, s);
    }

    @Override
    public Page<Message> findMessageByRecipientId(Long recipientId, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sort, sortProperties,
                this.messageMaxFetch, this.defaultMessageSortType, this.defaultMessageSortProperties);
        return messageRepository.findMessageByRecipientId(recipientId, p);
    }

    @Override
    public List<Message> findMessageByRecipientId(Long recipientId, String sort, List<String> sortProperties) {
        Sort s = getSortForRestService(sort, sortProperties,
                this.defaultMessageSortType, this.defaultMessageSortProperties);
        return messageRepository.findMessageByRecipientId(recipientId, s);
    }

    @Override
    public List<Message> findMessageByRecipientId(Long recipientId) {
        return messageRepository.findMessageByRecipientId(recipientId);
    }

    @Override
    public Optional<Message> update(Message message) {
        return Optional.ofNullable(messageRepository.save(message));
    }
}
