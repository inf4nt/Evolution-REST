package evolution.dto.transfer;

import evolution.dto.model.*;
import evolution.model.Dialog;
import evolution.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DialogDTOTransfer implements TransferDTOLazy<DialogDTO, DialogDTOLazy, Dialog> {

    private final MessageDTOTransfer messageDTOTransfer;

    private final UserDTOTransfer userDTOTransfer;

    private final ModelMapper modelMapper;

    @Autowired
    public DialogDTOTransfer(MessageDTOTransfer messageDTOTransfer,
                             UserDTOTransfer userDTOTransfer,
                             ModelMapper modelMapper) {
        this.messageDTOTransfer = messageDTOTransfer;
        this.userDTOTransfer = userDTOTransfer;
        this.modelMapper = modelMapper;
    }

    @Override
    public DialogDTO modelToDTO(Dialog dialog) {
        return modelMapper.map(dialog, DialogDTO.class);
    }

    @Override
    public DialogDTOLazy modelToDTOLazy(Dialog dialog) {
        DialogDTOLazy dialogDTOLazy = new DialogDTOLazy();
        dialogDTOLazy.setId(dialog.getId());

        dialogDTOLazy.setFirst(userDTOTransfer.modelToDTO(dialog.getFirst()));
        dialogDTOLazy.setSecond(userDTOTransfer.modelToDTO(dialog.getSecond()));

        dialogDTOLazy.setCreateDate(dialog.getCreateDate());

        List<MessageDTO> list = new ArrayList<>();
        dialog.getMessageList().forEach(o -> {
            list.add(messageDTOTransfer.modelToDTO(o));
        });
        dialogDTOLazy.setMessageList(list);
        return dialogDTOLazy;
    }

    public DialogDTO modelToDTO(Dialog dialog, Long auth) {
        return modelToDTO(dialog, new User(auth));
    }

    public DialogDTO modelToDTO(Dialog dialog, User auth) {
        DialogDTO dialogDTO = modelMapper.map(dialog, DialogDTO.class);
        UserDTO first = dialogDTO.getFirst();
        if (!auth.getId().equals(first.getId())) {
            dialogDTO.setFirst(dialogDTO.getSecond());
            dialogDTO.setSecond(first);
        }
        return dialogDTO;
    }

    public DialogDTOLazy modelToDTOLazy(Dialog dialog, User auth) {
        DialogDTOLazy dialogDTOLazy = new DialogDTOLazy();
        dialogDTOLazy.setId(dialog.getId());

        if (dialog.getFirst().getId().equals(auth.getId())) {
            dialogDTOLazy.setFirst(userDTOTransfer.modelToDTO(auth));
            dialogDTOLazy.setSecond(userDTOTransfer.modelToDTO(dialog.getSecond()));
        } else if (dialog.getSecond().getId().equals(auth.getId())) {
            dialogDTOLazy.setFirst(userDTOTransfer.modelToDTO(dialog.getSecond()));
            dialogDTOLazy.setSecond(userDTOTransfer.modelToDTO(auth));
        } else {
            dialogDTOLazy.setFirst(userDTOTransfer.modelToDTO(dialog.getFirst()));
            dialogDTOLazy.setSecond(userDTOTransfer.modelToDTO(dialog.getSecond()));
        }

        dialogDTOLazy.setCreateDate(dialog.getCreateDate());

        List<MessageDTO> list = new ArrayList<>();
        dialog.getMessageList().forEach(o -> {
            list.add(messageDTOTransfer.modelToDTO(o, auth));
        });
        dialogDTOLazy.setMessageList(list);
        return dialogDTOLazy;
    }
}
