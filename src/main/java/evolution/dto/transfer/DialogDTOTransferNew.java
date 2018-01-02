package evolution.dto.transfer;

import evolution.dto.model.*;
import evolution.model.Dialog;
import evolution.model.User;
import evolution.service.SecuritySupportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DialogDTOTransferNew {

    @Autowired
    private MessageDTOTransferNew messageDTOTransferNew;

    @Autowired
    private UserDTOTransferNew userDTOTransferNew;

    @Autowired
    private SecuritySupportService securitySupportService;

    @Autowired
    private ModelMapper modelMapper;

    public DialogDTO modelToDTO(Dialog dialog) {
        return modelMapper.map(dialog, DialogDTO.class);
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

    public DialogDTOLazy modelToDTOLazy(Dialog dialog) {
        DialogDTOLazy dialogDTOLazy = new DialogDTOLazy();
        dialogDTOLazy.setId(dialog.getId());

        User auth = securitySupportService.getAuthenticationPrincipal().getUser();

        dialogDTOLazy.setFirst(userDTOTransferNew.modelToDTO(dialog.getFirst()));
        dialogDTOLazy.setSecond(userDTOTransferNew.modelToDTO(dialog.getSecond()));

        dialogDTOLazy.setCreateDate(dialog.getCreateDate());

        List<MessageDTO> list = new ArrayList<>();
        dialog.getMessageList().forEach(o -> {
            list.add(messageDTOTransferNew.modelToDTO(o));
        });
        dialogDTOLazy.setMessageList(list);
        return dialogDTOLazy;
    }

    public DialogDTOLazy modelToDTOLazy(Dialog dialog, User auth) {
        DialogDTOLazy dialogDTOLazy = new DialogDTOLazy();
        dialogDTOLazy.setId(dialog.getId());

        if (dialog.getFirst().getId().equals(auth.getId())) {
            dialogDTOLazy.setFirst(userDTOTransferNew.modelToDTO(auth));
            dialogDTOLazy.setSecond(userDTOTransferNew.modelToDTO(dialog.getSecond()));
        } else if (dialog.getSecond().getId().equals(auth.getId())) {
            dialogDTOLazy.setFirst(userDTOTransferNew.modelToDTO(dialog.getSecond()));
            dialogDTOLazy.setSecond(userDTOTransferNew.modelToDTO(auth));
        } else {
            dialogDTOLazy.setFirst(userDTOTransferNew.modelToDTO(dialog.getFirst()));
            dialogDTOLazy.setSecond(userDTOTransferNew.modelToDTO(dialog.getSecond()));
        }

        dialogDTOLazy.setCreateDate(dialog.getCreateDate());

        List<MessageDTO> list = new ArrayList<>();
        dialog.getMessageList().forEach(o -> {
            list.add(messageDTOTransferNew.modelToDTO(o, auth));
        });
        dialogDTOLazy.setMessageList(list);
        return dialogDTOLazy;
    }




//    public Dialog dtoToModel(DialogDTO dialogDTO) {
//        return modelMapper.map(dialogDTO, Dialog.class);
//    }
//
//    public Dialog dtoToModel(DialogDTOLazy dialogLazyDTO) {
//        Dialog dialog = dtoToModel(dialogLazyDTO);
//        List<Message> messages = new ArrayList<>();
//        dialogLazyDTO.getMessageList().forEach(o -> {
//            messages.add(messageDTOTransfer.dtoToModel(o));
//        });
//        dialog.setMessageList(messages);
//        return dialog;
//    }
}
