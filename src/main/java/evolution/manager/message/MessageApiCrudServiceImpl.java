package evolution.manager.message;

import evolution.crud.api.MessageCrudManagerService;
import evolution.dto.model.MessagePrivateDTO;
import evolution.dto.model.MessageSaveDTO;
import evolution.dto.model.MessageUpdateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageApiCrudServiceImpl implements MessageApiCrudService {

    private final MessageCrudManagerService messageCrudManagerService;

    private final ModelMapper modelMapper;

    @Autowired
    public MessageApiCrudServiceImpl(MessageCrudManagerService messageCrudManagerService,
                                     ModelMapper modelMapper) {
        this.messageCrudManagerService = messageCrudManagerService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MessagePrivateDTO> findMessageBySenderAndRecipient(Long sender, Long recipient) {
        return null;
    }

    @Override
    public Optional<MessagePrivateDTO> findMessageByIdAndSenderOrRecipient(Long id, Long userId) {
        return Optional.empty();
    }

    @Override
    public MessagePrivateDTO create(MessageSaveDTO messageSaveDTO) {
        return null;
    }

    @Override
    public MessagePrivateDTO update(MessageUpdateDTO messageUpdateDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<MessagePrivateDTO> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<MessagePrivateDTO> findAll() {
        return null;
    }

    @Override
    public Page<MessagePrivateDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public void delete(MessagePrivateDTO messagePrivateDTO) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
