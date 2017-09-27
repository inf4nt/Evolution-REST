package evolution.service;

import evolution.model.dialog.Dialog;
import evolution.model.friend.Friends;
import evolution.model.message.Message;
import evolution.model.user.User;
import evolution.model.user.UserLight;
import evolution.security.model.CustomSecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Infant on 27.09.2017.
 */
@Service
public class TechnicalDataService {

    private final SecuritySupportService securitySupportService;

    @Autowired
    public TechnicalDataService(SecuritySupportService securitySupportService) {
        this.securitySupportService = securitySupportService;
    }

    public Message repairDialog(Message message) {
        message.setDialog(repairDialog(message.getDialog()));
        return message;
    }

    public List<Message> repairDialogForMessageList(List<Message> list) {
        return list
                .stream()
                .map(o -> repairDialog(o))
                .collect(Collectors.toList());
    }

    public Dialog repairDialog(Dialog dialog) {
        Optional<CustomSecurityUser> principal = securitySupportService.getPrincipal();
        if (principal.isPresent() && dialog != null) {
            User auth = principal.get().getUser();
            if (!dialog.getFirst().getId().equals(auth.getId())) {
                dialog.setSecond(dialog.getFirst());
                dialog.setFirst(new UserLight(auth));
            }
        }
        return dialog;
    }

    public List<Dialog> repairDialogForDialogList(List<Dialog> dialog) {
        return dialog
                .stream()
                .map(o -> repairDialog(o))
                .collect(Collectors.toList());
    }
}
