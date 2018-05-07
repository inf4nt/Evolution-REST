package evolution.manager.message;

import evolution.model.MessagePrivate;
import evolution.repository.MessagePrivateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
class MessagePrivateCrudManagerImpl implements MessagePrivateCrudManager {

    private final MessagePrivateRepository messagePrivateRepository;

    @Autowired
    public MessagePrivateCrudManagerImpl(MessagePrivateRepository messagePrivateRepository) {
        this.messagePrivateRepository = messagePrivateRepository;
    }

    @Override
    public Optional<MessagePrivate> findOne(Long aLong) {
        return Optional.ofNullable(messagePrivateRepository.findOne(aLong));
    }

    @Override
    public List<MessagePrivate> findAll() {
        return messagePrivateRepository.findAll();
    }

    @Override
    public Page<MessagePrivate> findAll(Pageable pageable) {
        return messagePrivateRepository.findAll(pageable);
    }

    @Override
    public void delete(MessagePrivate messagePrivate) {
        messagePrivateRepository.delete(messagePrivate);
    }

    @Override
    public void deleteById(Long aLong) {
        messagePrivateRepository.delete(aLong);
    }

    @Override
    public MessagePrivate save(MessagePrivate messagePrivate) {
        return messagePrivateRepository.save(messagePrivate);
    }

    @Override
    public List<MessagePrivate> findMessageBySenderAndRecipient(Long sender, Long recipient) {
        return messagePrivateRepository.findAllBySenderAndRecipient(sender, recipient);
    }

    @Override
    public Optional<MessagePrivate> findMessageByIdAndSenderOrRecipient(Long id, Long userId) {
        return messagePrivateRepository.findMessageByIdAndSenderOrRecipient(id, userId);
    }
}
