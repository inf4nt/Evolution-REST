package evolution.transfer;

import evolution.dto.MessageDTO;
import evolution.dto.UserDTO;
import evolution.model.Message;
import evolution.model.User;

import java.util.List;

/**
 * Created by Infant on 07.11.2017.
 */
public interface TransferDTO {

    MessageDTO modelToDTO(Message message);

    List<MessageDTO> modelToDTOListMessage(List<Message> message);

    UserDTO modelToDTO(User user);

    List<UserDTO> modelToDTOListUser(List<User> user);

//    Message DTOToModel(MessageDTO messageDTO);

//    User DTOToModel(UserDTO userDTO);

//    List<Message> DTOToModelListMessage(List<MessageDTO> messageDTO);

//    List<User> dTOToModelListUser(List<UserDTO> userDTO);
}
