package evolution.service;


import evolution.model.message.Message;
import evolution.model.user.UserLight;
import evolution.security.model.CustomSecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Infant on 04.09.2017.
 */
@Service
public class MessageTechService {

    private final SecuritySupportService securitySupportService;

    @Autowired
    public MessageTechService(SecuritySupportService securitySupportService) {
        this.securitySupportService = securitySupportService;
    }

    /**
     * Делает порядок в диалоге, поле First это аутентифицированный, second с кем у аутентифицированного диалог
     *
     * @param messageList
     * @return
     */
    public List<Message> repairMessageList(List<Message> messageList) {
        return messageList
                .stream()
                .map(this::repairMessage)
                .collect(Collectors.toList());
    }

    /**
     * Делает порядок в диалоге, поле First это аутентифицированный, second с кем у аутентифицированного диалог
     *
     * @param message
     * @return
     */

    public Message repairMessage(Message message) {
        Optional<CustomSecurityUser> optional = securitySupportService.getPrincipal();
        if(optional.isPresent()) {
            UserLight authUser = new UserLight(optional.get());
            System.out.println(message.getDialog());
            if (!message.getDialog().getFirst().equals(authUser)) {
                message.getDialog().setSecond(message.getDialog().getFirst());
                message.getDialog().setFirst(authUser);
            }
        }
        return message;
    }
}