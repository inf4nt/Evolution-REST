package evolution.crud;

import evolution.data.DialogRepository;
import evolution.data.MessageRepository;
import evolution.data.UserRepository;
import evolution.dto.MessageDTO;
import evolution.model.Dialog;
import evolution.model.Message;
import evolution.model.User;
import evolution.service.DateService;
import evolution.transfer.TransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 07.11.2017.
 */
@Service("messageManagerService")
public class ManagerServiceImpl implements MessageManagerService, PageableManager {

    private final MessageRepository messageRepository;

    private final DialogRepository dialogRepository;

    private final UserRepository userRepository;

    private final TransferDTO transferDTO;

    @Autowired
    private DateService dateService;

    @Autowired
    public ManagerServiceImpl(MessageRepository messageRepository, DialogRepository dialogRepository, UserRepository userRepository, TransferDTO transferDTO) {
        this.messageRepository = messageRepository;
        this.dialogRepository = dialogRepository;
        this.userRepository = userRepository;
        this.transferDTO = transferDTO;
    }

    @Override
    @Transactional
    public MessageDTO createNewMessage(Long iam, MessageDTO messageDTO) {
        Dialog dialog;
        Message message = new Message();
        Long sender = messageDTO.getSender().getId();
        Long recipient = messageDTO.getRecipient().getId();
        message.setDateDispatch(dateService.getCurrentDateInUTC());

        message.setMessage(messageDTO.getText());

        Optional<User> senderUser = userRepository.findOneUser(sender);
        Optional<User> recipientUser = userRepository.findOneUser(recipient);
        if (!senderUser.isPresent() || !recipientUser.isPresent()) {
            // todo throw custom exception
            throw new UnsupportedOperationException("User not found by id " + sender + ", " + recipient);
        }

        Optional<Dialog> dialogOptional = dialogRepository.findDialogByUsersLoadLazy(sender, recipient);
        if (dialogOptional.isPresent()) {
            //dialog exist
            dialog = dialogOptional.get();
        } else {
            //create dialog
            dialog = new Dialog();
            dialog.setCreateDate(dateService.getCurrentDateInUTC());
            dialog.setSecond(senderUser.get());
            dialog.setFirst(recipientUser.get());
            dialog = dialogRepository.save(dialog);
        }

        message.setDialog(dialog);
        message.setSender(senderUser.get());
        message.setActive(true);

        Message resultMessage = messageRepository.save(message);

        return transferDTO.modelToDTO(resultMessage);
    }

    @Override
    public Optional<MessageDTO> findOne(Long id) {
        Optional<Message> message = messageRepository.findOneMessage(id);
        return message.map(m -> transferDTO.modelToDTO(m));
    }

    @Override
    public Optional<MessageDTO> findOne(Long messageId, Long senderId) {
        Optional<Message> message = messageRepository.findOneMessage(messageId, senderId);
        return message.map(m -> transferDTO.modelToDTO(m));
    }

    @Override
    public void deleteMessage(Long messageId, Long senderId) {
        Optional<Message> message = messageRepository.findOneMessage(messageId, senderId);
        message.ifPresent(m -> messageRepository.delete(m));
    }

    @Override
    public void deleteMessage(Long messageId) {
        Optional<Message> message = messageRepository.findOneMessage(messageId);
        message.ifPresent(m -> messageRepository.delete(m));
    }

    @Override
    public void deleteMessageAndMaybeDialog(Long messageId, Long senderId) {
        Optional<Message> message = messageRepository.findOneMessage(messageId, senderId);
        if (message.isPresent()) {
            Dialog dialog = message.get().getDialog();
            if (dialog.getMessageList().size() == 1) {
                dialogRepository.delete(dialog);
            } else {
                dialog.getMessageList().remove(message.get());
                dialogRepository.save(dialog);
            }
        }
    }

    @Override
    public void deleteMessageAndMaybeDialog(Long messageId) {
        Optional<Message> message = messageRepository.findOneMessage(messageId);
        if (message.isPresent()) {
            Dialog dialog = message.get().getDialog();
            if (dialog.getMessageList().size() == 1) {
                dialogRepository.delete(dialog);
            } else {
                dialog.getMessageList().remove(message.get());
                dialogRepository.save(dialog);
            }
        }
    }

    @Override
    public Page<MessageDTO> findLastMessageInMyDialogs(Long iam, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable pageable = getPageable(page, size, sort, sortProperties);
        Page<Message> p = messageRepository.findLastMessageInMyDialogs(iam, pageable);

        return pageModelToPageDTO(p);
    }

    @Override
    public List<MessageDTO> findAllMessage(String sort, List<String> sortProperties) {
        Sort s = getSort(sort, sortProperties);
        List<Message> messageList = messageRepository.findAll(s);

        return transferDTO.modelToDTOListMessage(messageList);
    }

    @Override
    public Page<MessageDTO> findAllMessage(Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable pageable = getPageable(page, size, sort, sortProperties);
        Page<Message> p = messageRepository.findAll(pageable);

        return pageModelToPageDTO(p);
    }

    @Override
    public Page<MessageDTO> findMessageByDialog(Long user1, Long user2, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable pageable = getPageable(page, size, sort, sortProperties);
        Optional<Dialog> optional = dialogRepository.findDialogByUsers(user1, user2);
        if (optional.isPresent()) {
            Dialog dialog = optional.get();
            Page<Message> p = messageRepository.findMessageByDialog(dialog.getId(), pageable);

            return pageModelToPageDTO(p);
        } else {
            return new PageImpl<MessageDTO>(new ArrayList<>());
        }
    }

    @Override
    public Page<MessageDTO> findMessageByDialog(Long dialogId, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable pageable = getPageable(page, size, sort, sortProperties);
        Page<Message> p = messageRepository.findMessageByDialog(dialogId, pageable);
        return pageModelToPageDTO(p);
    }

    private Page<MessageDTO> pageModelToPageDTO(Page<Message> messagePage) {
        return messagePage.map(message -> transferDTO.modelToDTO(message));
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
    public Sort getSort(String sort, List<String> sortProperties) {
        return getSortForRestService(sort, sortProperties,
                this.defaultMessageSortType, this.defaultMessageSortProperties);
    }
}
