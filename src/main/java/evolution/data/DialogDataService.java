package evolution.data;

import evolution.model.dialog.Dialog;
import evolution.model.message.Message;
import evolution.model.user.UserLight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 04.09.2017.
 */
@Service
public class DialogDataService {

    private final DialogRepository dialogRepository;

    @Autowired
    public DialogDataService(DialogRepository dialogRepository) {
        this.dialogRepository = dialogRepository;
    }

    public List<Dialog> findAll() {
        return dialogRepository.findAll();
    }

    public void delete(Dialog dialog) {
        dialogRepository.delete(dialog);
    }

    /**
     * Перед удалением делает селект, чтоб каскадно удалить все связи
     *
     * @param id
     */

    public void delete(Long id) {
        Optional optional = findOne(id);
        if (optional.isPresent())
            dialogRepository.delete((Dialog) optional.get());
    }

    public Optional findOne(Long id) {
        return Optional.ofNullable(dialogRepository.findOne(id));
    }

    /**
     * метод сохраняет сообщения, проверит существует ли диалог, если не существует создаст новый
     * @param text
     * @param senderUserId
     * @param secondUserId
     * @return
     */
    public Dialog save(String text, Long senderUserId, Long secondUserId) {

        UserLight authUser = new UserLight(senderUserId);

        Optional optional = this.findDialogByUsers(secondUserId, authUser.getId());
        Message message = new Message();
        Dialog dialog;

        if (optional.isPresent()) {
            dialog = (Dialog) optional.get();  // dialog exist
        } else {
            dialog = new Dialog(authUser, new UserLight(secondUserId)); // dialog not exist
        }

        message.setMessage(text);
        message.setDialog(dialog);
        message.setSender(authUser);

        dialog.getMessageList().add(message);

        return this.dialogRepository.saveAndFlush(dialog);
    }

    public Optional findDialogByUsers(Long userid1, Long userid2) {
        return Optional.ofNullable(dialogRepository.findDialogByUsers(userid1, userid2));
    }
}
