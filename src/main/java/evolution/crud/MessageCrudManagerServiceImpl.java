package evolution.crud;

import evolution.crud.api.MessageCrudManagerService;
import evolution.model.Dialog;
import evolution.model.Message;
import evolution.model.User;
import evolution.repository.DialogRepository;
import evolution.repository.MessageRepository;
import evolution.repository.UserRepository;
import evolution.service.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 07.11.2017.
 */
@Service
public class MessageCrudManagerServiceImpl implements MessageCrudManagerService {

    private final MessageRepository messageRepository;

    private final DialogRepository dialogRepository;

    private final DateService dateService;

    private final UserRepository userRepository;

    @Autowired
    public MessageCrudManagerServiceImpl(MessageRepository messageRepository,
                                         DialogRepository dialogRepository,
                                         DateService dateService,
                                         UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.dialogRepository = dialogRepository;
        this.dateService = dateService;
        this.userRepository = userRepository;
    }


    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public List<Message> findAll(String sort, List<String> sortProperties) {
        Sort s = getSort(sort, sortProperties);
        return messageRepository.findAll(s);
    }

    @Override
    public Page<Message> findAll(Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageable(page, size, sort, sortProperties);
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
    public Message saveMessageAndMaybeCreateNewDialog(String text, Long senderId, Long recipientId) {
        Optional<Dialog> od = dialogRepository.findDialogByUsers(senderId, recipientId);
        Dialog dialog;
        Message message = new Message();

        Optional<User> os = userRepository.findOneUserById(senderId);
        if (os.isPresent()) {
            message.setSender(os.get());
        }

        if (od.isPresent()) {
            //dialog exist
            dialog = od.get();
        } else {
            // dialog not exist
            dialog = new Dialog();
            dialog.setCreateDate(dateService.getCurrentDateInUTC());

            Optional<User> or = userRepository.findOneUserById(recipientId);
            if (os.isPresent() && or.isPresent()) {
                dialog.setFirst(os.get());
                dialog.setSecond(or.get());
                message.setSender(os.get());
            }
            dialog = dialogRepository.save(dialog);
        }

        message.setDateDispatch(dateService.getCurrentDateInUTC());
        message.setDialog(dialog);
        message.setMessage(text);
        message.setActive(true);

        return messageRepository.save(message);
    }

    @Override
    public void delete(Long aLong) {
        Optional<Message> message = messageRepository.findOneMessageById(aLong);
        message.ifPresent(m -> messageRepository.delete(m));
    }

    @Override
    @Transactional
    public void deleteMessageAndMaybeDialog(Long messageId) {
        Optional<Message> message = messageRepository.findOneMessageById(messageId);
        if (message.isPresent()) {
            Dialog dialog = message.get().getDialog();
            List<Message> messageList = dialog.getMessageList();
            if (!messageList.isEmpty()) {
                if (messageList.size() == 1) {
                    dialogRepository.delete(dialog);
                } else {
                    messageList.remove(message.get());
                    dialogRepository.save(dialog);
                }
            }
        }
    }


    @Override
    public Optional<Message> findOneByMessageIdAndSenderId(Long messageId, Long senderId) {
        return messageRepository.findOneMessageByMessageIdAndSenderId(messageId, senderId);
    }

    @Override
    public Page<Message> findLastMessageInMyDialog(Long iam, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageable(page, size, sort, sortProperties);
        return messageRepository.findLastMessageInMyDialog(iam, p);
    }

    @Override
    public List<Message> findLastMessageInMyDialog(Long iam, String sort, List<String> sortProperties) {
        Sort s = getSort(sort, sortProperties);
        return messageRepository.findLastMessageInMyDialog(iam, s);
    }

    @Override
    public Page<Message> findMessageBySenderId(Long senderId, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageable(page, size, sort, sortProperties);
        return messageRepository.findMessageBySenderId(senderId, p);
    }

    @Override
    public List<Message> findMessageBySenderId(Long senderId, String sort, List<String> sortProperties) {
        Sort s = getSort(sort, sortProperties);
        return messageRepository.findMessageBySenderId(senderId, s);
    }

    @Override
    public Page<Message> findMessageByRecipientId(Long recipientId, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageable(page, size, sort, sortProperties);
        return messageRepository.findMessageByRecipientId(recipientId, p);
    }

    @Override
    public List<Message> findMessageByRecipientId(Long recipientId, String sort, List<String> sortProperties) {
        Sort s = getSort(sort, sortProperties);
        return messageRepository.findMessageByRecipientId(recipientId, s);
    }


    @Value("${model.message.maxfetch}")
    private Integer messageMaxFetch;

    @Value("${model.message.defaultsort}")
    private String defaultMessageSortType;

    @Value("${model.message.defaultsortproperties}")
    private String defaultMessageSortProperties;

    @Override
    public Pageable getPageable(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return getPageableForRestService(page, size, sortType, sortProperties,
                this.messageMaxFetch, this.defaultMessageSortType, this.defaultMessageSortProperties);
    }

    @Override
    public Pageable getPageable(Integer page, Integer size) {
        return getPageableForRestService(page, size,
                this.messageMaxFetch);
    }

    @Override
    public Sort getSort(String sort, List<String> sortProperties) {
        return getSortForRestService(sort, sortProperties,
                this.defaultMessageSortType, this.defaultMessageSortProperties);
    }
}
