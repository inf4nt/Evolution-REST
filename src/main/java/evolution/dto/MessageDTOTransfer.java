package evolution.dto;

import evolution.dto.model.DialogDTO;
import evolution.dto.model.MessageDTO;
import evolution.model.Message;
import evolution.model.User;
import evolution.security.model.CustomSecurityUser;
import evolution.service.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Infant on 13.11.2017.
 */
@Service
public class MessageDTOTransfer {

    private final DateService dateService;

    private final UserDTOTransfer userDTOTransfer;

    private final DialogDTOTransfer dialogDTOTransfer;

    @Autowired
    public MessageDTOTransfer(DateService dateService,
                              UserDTOTransfer userDTOTransfer,
                              DialogDTOTransfer dialogDTOTransfer) {
        this.dateService = dateService;
        this.userDTOTransfer = userDTOTransfer;
        this.dialogDTOTransfer = dialogDTOTransfer;
    }

    public MessageDTO modelToDTO(Message message, Optional<CustomSecurityUser> auth) {

        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());

        dto.setCreatedDateTimestamp(message.getDateDispatch().getTime());
        dto.setCreatedDateString(dateService.formatDateUTC(message.getDateDispatch().getTime()));

        dto.setText(message.getMessage());
        dto.setSender(userDTOTransfer.modelToDTO(message.getSender()));


        dto.setDialogDTO(dialogDTOTransfer.modelToDTO(message.getDialog(), auth));

        return dto;
    }





}
