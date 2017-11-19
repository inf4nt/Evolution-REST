package evolution.dto;

import evolution.dto.model.DialogDTO;
import evolution.model.Dialog;
import evolution.model.User;
import evolution.security.model.CustomSecurityUser;
import evolution.service.DateService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        DialogDTO dto = new DialogDTO();
        dto.setId(dialog.getId());

        dto.setFirst(userDTOTransfer.modelToDTO(dialog.getFirst()));
        dto.setSecond(userDTOTransfer.modelToDTO(dialog.getSecond()));

        dto.setCreatedDateTimestamp(dialog.getCreateDate().getTime());
        dto.setCreatedDateString(dateService.formatDateUTC(dialog.getCreateDate()));
        return dto;
    }


    public Dialog dtoToModel(DialogDTO dialogDTO) {
        return modelMapper.map(dialogDTO, Dialog.class);
    }
}
