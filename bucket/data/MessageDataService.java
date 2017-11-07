package evolution.data;

import evolution.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 22.10.2017.
 */
@Service
public class MessageDataService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageDataService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Message> findOne(Long id) {
        return Optional.ofNullable(messageRepository.findOne(id));
    }

    @Transactional(readOnly = true)
    public List<Message> findOneByUsers(Long user1, Long user2) {
        return messageRepository.findMessageByUsers(user1, user2);
    }

    @Transactional(readOnly = true)
    public Page<Message> findAll(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Message> findAll(Sort sort) {
        return messageRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Message> findMessageByDialog(Long dialogId, Pageable pageable) {
        return messageRepository.findMessageByDialog(dialogId, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Message> findOne(Long id, Long senderId) {
        return Optional.ofNullable(messageRepository.findOne(id, senderId));
    }

    @Transactional(readOnly = true)
    public Page<Message> findLastUserMessageInDialogWhereUserId(Long userId, Pageable pageable) {
        return messageRepository.findLastUserMessageInDialogWhereUserId(userId, pageable);
    }

    @Transactional(readOnly = true)
    public List<Message> findLastUserMessageInDialogWhereUserId(Long userId) {
        return messageRepository.findLastUserMessageInDialogWhereUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Message> findLastUserMessageInDialogWhereUserId(Long userId, Sort sort) {
        return messageRepository.findLastUserMessageInDialogWhereUserId(userId, sort);
    }

    @Transactional(readOnly = true)
    public Page<Message> findMessageByDialogId(Long dialogId, Pageable pageable) {
        return messageRepository.findMessageByDialog(dialogId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Message> findMessageByDialogUsers(Long user1, Long user2, Pageable pageable) {
        return messageRepository.findMessageByDialogUsers(user1, user2, pageable);
    }

    @Transactional
    public Message save(Message message) {
        return messageRepository.save(message);
    }
}
