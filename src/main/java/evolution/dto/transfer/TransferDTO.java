package evolution.dto.transfer;

import evolution.dto.model.DialogDTO;
import evolution.dto.model.MessageDTO;
import evolution.dto.model.UserDTO;
import evolution.model.Dialog;
import evolution.model.Message;
import evolution.model.User;

import java.util.List;

/**
 * Created by Infant on 07.11.2017.
 */
public interface TransferDTO {

    MessageDTO modelToDTO(Message message);

    List<MessageDTO> modelToDTOListMessage(List<Message> message);

    DialogDTO modelToDTO(Dialog dialog);

    List<DialogDTO> modelToDTOListDialog(List<Dialog> dialog);

    UserDTO modelToDTO(User user);

    List<UserDTO> modelToDTOListUser(List<User> user);
}
