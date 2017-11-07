package evolution.transfer;

import evolution.dto.MessageDTO;
import evolution.dto.UserDTO;
import evolution.model.Dialog;
import evolution.model.Message;
import evolution.model.User;
import evolution.service.DateService;
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

    @Autowired
    public TransferDTOImpl(DateService dateService) {
        this.dateService = dateService;
    }

    @Override
    public MessageDTO modelToDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();

        messageDTO.setId(message.getId());

        Dialog dialog = message.getDialog();
        User first = dialog.getFirst();
        User second = dialog.getSecond();

        if (first.getId() > second.getId()) {
            messageDTO.setFirst(modelToDTO(first));
            messageDTO.setSecond(modelToDTO(second));
        } else {
            messageDTO.setFirst(modelToDTO(second));
            messageDTO.setSecond(modelToDTO(first));
        }

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

    @Override
    public List<MessageDTO> modelToDTOListMessage(List<Message> message) {
        return message.stream().map(m ->  modelToDTO(m)).collect(Collectors.toList());
    }

    @Override
    public List<Message> DTOToModelListMessage(List<MessageDTO> messageDTO) {
        return messageDTO.stream().map(m ->  DTOToModel(m)).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> modelToDTOListUser(List<User> user) {
        return user.stream().map(m ->  modelToDTO(m)).collect(Collectors.toList());
    }

    @Override
    public List<User> dTOToModelListUser(List<UserDTO> userDTO) {
        return userDTO.stream().map(m ->  DTOToModel(m)).collect(Collectors.toList());
    }
}
