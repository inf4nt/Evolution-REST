package evolution.manager.message;

import evolution.dto.model.MessagePrivateDTO;
import evolution.dto.model.MessageSaveDTO;
import evolution.dto.model.MessageUpdateDTO;
import evolution.manager.AbstracrApiCrudService;
import java.util.List;
import java.util.Optional;

public interface MessageApiCrudService extends AbstracrApiCrudService<MessagePrivateDTO, Long> {

    List<MessagePrivateDTO> findMessageBySenderAndRecipient(Long sender, Long recipient);

    Optional<MessagePrivateDTO> findMessageByIdAndSenderOrRecipient(Long id, Long userId);

    MessagePrivateDTO create(MessageSaveDTO messageSaveDTO);

    MessagePrivateDTO update(MessageUpdateDTO messageUpdateDTO);

    void delete(Long id);
}
