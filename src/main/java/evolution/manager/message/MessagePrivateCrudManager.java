package evolution.manager.message;

import evolution.manager.AbstractManager;
import evolution.model.MessagePrivate;

import java.util.List;
import java.util.Optional;

interface MessagePrivateCrudManager extends AbstractManager<MessagePrivate, Long> {

    List<MessagePrivate> findMessageBySenderAndRecipient(Long sender, Long recipient);

    Optional<MessagePrivate> findMessageByIdAndSenderOrRecipient(Long id, Long userId);
}
