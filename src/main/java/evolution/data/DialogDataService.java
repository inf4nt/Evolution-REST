package evolution.data;

import evolution.model.dialog.Dialog;
import evolution.model.message.Message;
import evolution.model.user.UserLight;
import evolution.service.TechnicalDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.acl.LastOwnerException;
import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 04.09.2017.
 */
@Service
public class DialogDataService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DialogRepository dialogRepository;

    private final TechnicalDataService technicalDataService;

    private final MessageRepository messageRepository;

    @Autowired
    public DialogDataService(DialogRepository dialogRepository, TechnicalDataService technicalDataService, MessageRepository messageRepository) {
        this.dialogRepository = dialogRepository;
        this.technicalDataService = technicalDataService;
        this.messageRepository = messageRepository;
    }

    @Transactional(readOnly = true)
    public List<Dialog> findAll() {
        return technicalDataService
                .repairDialogForDialogList(dialogRepository.findAll());
    }

    @Transactional
    public void delete(Dialog dialog) {
        dialogRepository.delete(dialog);
    }

    @Transactional
    public void deleteMessageInDialog(Message message) {
        Optional<Dialog> optional = findOne(message.getDialog().getId());

        if (optional.isPresent()) {
            Dialog dialog = optional.get();
            if (dialog.getMessageList().isEmpty() || dialog.getMessageList().size() == 1) {
                LOGGER.info("delete dialog " + dialog);
                dialogRepository.delete(dialog);
            } else {
                dialog.getMessageList().remove(message);
                dialogRepository.save(dialog);
                LOGGER.info("delete message " + message);
            }
        }

    }

    @Transactional
    public void delete(Long id) {
        Optional<Dialog> optional = findOne(id);
        optional.ifPresent(dialog -> {
            dialog.getMessageList().size();
            dialogRepository.delete(dialog);
        });
    }

    @Transactional(readOnly = true)
    public Optional<Dialog> findOne(Long id) {
        return Optional.ofNullable(dialogRepository.findOne(id));
    }

    @Transactional
    public Dialog save(String text, Long senderUserId, Long secondUserId) {

        UserLight authUser = new UserLight(senderUserId);

        Optional<Dialog> optional = this.findDialogByUsers(secondUserId, authUser.getId());
        Message message = new Message();
        Dialog dialog;

        if (optional.isPresent()) {
            dialog = optional.get();  // dialog exist
        } else {
            dialog = new Dialog(new UserLight(senderUserId), new UserLight(secondUserId)); // dialog not exist
        }

        message.setMessage(text);
        message.setDialog(dialog);
        message.setSender(authUser);

        dialog.getMessageList().add(message);

        return this.dialogRepository.saveAndFlush(dialog);
    }

    @Transactional(readOnly = true)
    public Optional<Dialog> findDialogByUsers(Long userid1, Long userid2) {
        Dialog dialog = dialogRepository.findDialogByUsers(userid1, userid2);
        if (dialog != null) {
            return Optional.of(technicalDataService.repairDialog(dialog));
        } else {
            return Optional.empty();
        }
    }
}
