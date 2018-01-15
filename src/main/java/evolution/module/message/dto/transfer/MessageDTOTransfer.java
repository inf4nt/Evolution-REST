package evolution.message.dto.transfer;

import evolution.dto.TransferDTO;
import evolution.message.dto.DialogDTO;
import evolution.message.dto.MessageDTO;
import evolution.user.dto.UserDTO;
import evolution.message.model.Message;
import evolution.user.model.User;
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

    @Autowired
    public MessageDTOTransfer(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageDTO modelToDTO(Message message) {
        return modelMapper.map(message, MessageDTO.class);
    }

    public MessageDTO modelToDTO(Message message, User auth) {
        MessageDTO messageDTO = modelMapper.map(message, MessageDTO.class);
        DialogDTO dialogDTO = modelMapper.map(message.getDialog(), DialogDTO.class);
        UserDTO first = dialogDTO.getFirst();
        if (!auth.getId().equals(first.getId())) {
            dialogDTO.setFirst(dialogDTO.getSecond());
            dialogDTO.setSecond(first);
        }
        messageDTO.setDialog(dialogDTO);
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
}
