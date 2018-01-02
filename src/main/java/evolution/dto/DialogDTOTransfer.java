package evolution.dto;

import evolution.dto.modelOld.DialogDTO;
import evolution.dto.modelOld.DialogFullDTO;
import evolution.dto.modelOld.UserDTO;
import evolution.model.Dialog;
import evolution.model.User;
import evolution.security.model.CustomSecurityUser;
import evolution.service.DateService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Infant on 13.11.2017.
 */
@Service
public class DialogDTOTransfer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDTOTransfer userDTOTransfer;

    private final DateService dateService;

    private final ModelMapper modelMapper;

    @Autowired
    public DialogDTOTransfer(UserDTOTransfer userDTOTransfer,
                             DateService dateService,
                             ModelMapper modelMapper) {
        this.userDTOTransfer = userDTOTransfer;
        this.dateService = dateService;
        this.modelMapper = modelMapper;
    }

    public DialogDTO modelToDTO(Dialog dialog, Optional<CustomSecurityUser> auth) {
        DialogDTO dto;
        if (auth == null || !auth.isPresent()) {
            logger.info("auth is null");
            dto = modelMapper.map(dialog, DialogDTO.class);
        } else {
            dto = new DialogDTO();
            dto.setId(dialog.getId());
            // auth user always FIRST !
            User a = auth.get().getUser();
            if (dialog.getFirst().getId().equals(a.getId())) {
                dto.setFirst(userDTOTransfer.modelToDTO(dialog.getFirst()));
                dto.setSecond(userDTOTransfer.modelToDTO(dialog.getSecond()));
            } else {
                dto.setFirst(userDTOTransfer.modelToDTO(dialog.getSecond()));
                dto.setSecond(userDTOTransfer.modelToDTO(dialog.getFirst()));
            }
        }

        dto.setCreatedDateTimestamp(dialog.getCreateDate().getTime());
        dto.setCreatedDateString(dateService.formatDateUTC(dialog.getCreateDate()));
        return dto;
    }

    public DialogDTO modelToDTO(Dialog dialog, User auth) {
        DialogDTO dto = new DialogDTO();
        dto.setId(dialog.getId());
        // auth user always FIRST !

        if (dialog.getFirst().getId().equals(auth.getId())) {
            dto.setFirst(userDTOTransfer.modelToDTO(dialog.getFirst()));
            dto.setSecond(userDTOTransfer.modelToDTO(dialog.getSecond()));
        } else {
            dto.setFirst(userDTOTransfer.modelToDTO(dialog.getSecond()));
            dto.setSecond(userDTOTransfer.modelToDTO(dialog.getFirst()));
        }

        dto.setCreatedDateTimestamp(dialog.getCreateDate().getTime());
        dto.setCreatedDateString(dateService.formatDateUTC(dialog.getCreateDate()));
        return dto;
    }

    public DialogDTO modelToDTO(Dialog dialog) {
        DialogDTO dto = modelMapper.map(dialog, DialogDTO.class);
        dto.setCreatedDateTimestamp(dialog.getCreateDate().getTime());
        dto.setCreatedDateString(dateService.formatDateUTC(dialog.getCreateDate()));
        return dto;
    }

    public DialogFullDTO modelToDTOFull(Dialog dialog) {
        DialogFullDTO d = new DialogFullDTO();

        List<DialogFullDTO.Message> messageList = dialog.getMessageList()
                .stream()
                .map(o -> {
                    DialogFullDTO.Message m = new DialogFullDTO.Message();
                    m.setSender(userDTOTransfer.modelToDTO(o.getSender()));
                    m.setId(o.getId());
                    m.setText(o.getMessage());
                    m.setDialogId(o.getDialog().getId());
                    m.setCreatedDateTimestamp(o.getDateDispatch().getTime());
                    m.setCreatedDateString(dateService.formatDateUTC(o.getDateDispatch()));
                    return m;
                })
                .collect(Collectors.toList());

        d.setId(dialog.getId());
        d.setFirst(userDTOTransfer.modelToDTO(dialog.getFirst()));
        d.setSecond(userDTOTransfer.modelToDTO(dialog.getSecond()));
        d.setCreatedDateTimestamp(dialog.getCreateDate().getTime());
        d.setCreatedDateString(dateService.formatDateUTC(dialog.getCreateDate()));
        d.setMessageList(messageList);

        return d;
    }

    public DialogFullDTO modelToDTOFull(Dialog dialog, Long auth) {
        DialogFullDTO d = modelToDTOFull(dialog);

        if (!auth.equals(d.getFirst().getId())) {
            UserDTO f = d.getSecond();
            d.setSecond(d.getFirst());
            d.setFirst(f);
        }

        return d;
    }

    public DialogFullDTO modelToDTOFull(Dialog dialog, User auth) {
        return modelToDTOFull(dialog, auth.getId());
    }

    public Dialog dtoToModel(DialogDTO dialogDTO) {
        return modelMapper.map(dialogDTO, Dialog.class);
    }
}
