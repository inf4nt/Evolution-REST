package evolution.transfer;

import evolution.dto.MessageDTO;
import evolution.dto.UserDTO;
import evolution.model.Dialog;
import evolution.model.Message;
import evolution.model.User;
import evolution.service.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Infant on 07.11.2017.
 */
@Service
public class TransferDTOImpl implements TransferDTO {

    private final DateService dateService;

    @Autowired
    public TransferDTOImpl(DateService dateService) {
        this.dateService = dateService;
    }

    @Override
    public MessageDTO modelToDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();

        messageDTO.setId(message.getId());

        Dialog dialog = message.getDialog();
        messageDTO.setSender(modelToDTO(dialog.getFirst()));
        messageDTO.setInterlocutorId(modelToDTO(dialog.getSecond()));

        messageDTO.setSender(modelToDTO(message.getSender()));
        messageDTO.setText(message.getMessage());

        messageDTO.setCreatedDateTimestamp(message.getDateDispatch().getTime());

        messageDTO.setCreatedDateString(dateService.getStringDate(message.getDateDispatch().getTime()));

        return messageDTO;
    }

    @Override
    public Message DTOToModel(MessageDTO messageDTO) {
        Message message = new Message();
        message.setId(messageDTO.getId());


        return null;
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
    public User DTOToModel(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setNickname(userDTO.getNickname());
        return user;
    }
}
