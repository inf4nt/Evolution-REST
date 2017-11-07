package evolution.transfer;

import evolution.dto.MessageDTO;
import evolution.dto.UserDTO;
import evolution.model.Dialog;
import evolution.model.Message;
import evolution.model.User;
import evolution.service.DateService;
import evolution.service.SecuritySupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Infant on 07.11.2017.
 */
@Service
public class TransferDTOImpl implements TransferDTO {

    private final DateService dateService;

    private final SecuritySupportService securitySupportService;

    @Autowired
    public TransferDTOImpl(DateService dateService, SecuritySupportService securitySupportService) {
        this.dateService = dateService;
        this.securitySupportService = securitySupportService;
    }

    @Override
    public MessageDTO modelToDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();

        messageDTO.setId(message.getId());

        Dialog dialog = message.getDialog();
        User first = dialog.getFirst();
        User second = dialog.getSecond();
        User sender = message.getSender();

        messageDTO.setSender(modelToDTO(sender));

        if (sender.getId().equals(first.getId())) {
            messageDTO.setRecipient(modelToDTO(second));
        } else {
            messageDTO.setRecipient(modelToDTO(first));
        }

        messageDTO.setSender(modelToDTO(message.getSender()));
        messageDTO.setText(message.getMessage());

        messageDTO.setCreatedDateTimestamp(message.getDateDispatch().getTime());

        messageDTO.setCreatedDateString(dateService.getStringDate(message.getDateDispatch().getTime()));

        return messageDTO;
    }

    @Override
    public UserDTO modelToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setNickname(user.getNickname());
        userDTO.setRole(user.getRole().name());
        return userDTO;
    }

    @Override
    public List<MessageDTO> modelToDTOListMessage(List<Message> message) {
        return message.stream().map(m -> modelToDTO(m)).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> modelToDTOListUser(List<User> user) {
        return user.stream().map(m -> modelToDTO(m)).collect(Collectors.toList());
    }
}
