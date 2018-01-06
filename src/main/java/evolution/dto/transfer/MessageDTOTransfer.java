package evolution.dto.transfer;

import evolution.dto.model.MessageDTO;
import evolution.model.Message;
import evolution.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageDTOTransfer implements TransferDTO<MessageDTO, Message> {

    private final ModelMapper modelMapper;

    private final DialogDTOTransfer dialogDTOTransfer;

    @Autowired
    public MessageDTOTransfer(ModelMapper modelMapper, DialogDTOTransfer dialogDTOTransfer) {
        this.modelMapper = modelMapper;
        this.dialogDTOTransfer = dialogDTOTransfer;
    }

    @Override
    public MessageDTO modelToDTO(Message message) {
        return modelMapper.map(message, MessageDTO.class);
    }

    public MessageDTO modelToDTO(Message message, User auth) {
        MessageDTO messageDTO = modelMapper.map(message, MessageDTO.class);
        messageDTO.setDialog(dialogDTOTransfer.modelToDTO(message.getDialog(), auth));
        return messageDTO;
    }

    public MessageDTO modelToDTO(Message message, Long auth) {
        return modelToDTO(message, new User(auth));
    }

    public List<MessageDTO> modelToDTO(List<Message> list, User auth) {
        return list
                .stream()
                .map(o -> modelToDTO(o, auth))
                .collect(Collectors.toList());
    }

    public List<MessageDTO> modelToDTO(List<Message> list, Long auth) {
        return list
                .stream()
                .map(o -> modelToDTO(o, auth))
                .collect(Collectors.toList());
    }

    public Page<MessageDTO> modelToDTO(Page<Message> page, User auth) {
        return page
                .map(o -> modelToDTO(o, auth));
    }

    public Page<MessageDTO> modelToDTO(Page<Message> page, Long auth) {
        return page
                .map(o -> modelToDTO(o, auth));
    }

    public Optional<MessageDTO> modelToDTO(Optional<Message> optional, User auth) {
        return optional
                .map(o -> modelToDTO(o, auth));
    }

    public Optional<MessageDTO> modelToDTO(Optional<Message> optional, Long auth) {
        return optional
                .map(o -> modelToDTO(o, auth));
    }


//    public Message dtoToModel(MessageDTO messageDTO) {
//        return modelMapper.map(messageDTO, Message.class);
//    }
}
