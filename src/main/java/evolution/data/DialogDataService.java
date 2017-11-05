package evolution.data;

import evolution.model.Dialog;
import evolution.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 23.10.2017.
 */
@Service
public class DialogDataService {

    private final DialogRepository dialogRepository;

    @Autowired
    public DialogDataService(DialogRepository dialogRepository) {
        this.dialogRepository = dialogRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Dialog> findOne(Long id) {
        return Optional.ofNullable(dialogRepository.findOne(id));
    }

    @Transactional(readOnly = true)
    public Page<Dialog> findAll(Pageable pageable) {
        return dialogRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Dialog> findAll(Sort sort) {
        return dialogRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    public Page<Dialog> findDialogWhereUsers(Long user1, Long user2, Pageable pageable) {
        return dialogRepository.findDialogWhereUsers(user1, user2, pageable);
    }

    @Transactional(readOnly = true)
    public List<Dialog> findDialogWhereUsers(Long user1, Long user2, Sort sort) {
        return dialogRepository.findDialogWhereUsers(user1, user2, sort);
    }

    @Transactional(readOnly = true)
    public Page<Dialog> findAllDialogByUserLoadLazy(Long userId, Pageable pageable) {
        return dialogRepository.findAllDialogByUserLoadLazy(userId, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Dialog> findDialogByIdAndSomeUser(Long dialogId, Long someUserId) {
        return dialogRepository.findDialogByIdAndSomeUser(dialogId, someUserId);
    }

    @Transactional(readOnly = true)
    public Page<Dialog> findAllDialogByUser(Long userId, Pageable pageable) {
        return dialogRepository.findAllDialogByUser(userId, pageable);
    }

    @Transactional
    public Dialog save(Dialog dialog) {
        return dialogRepository.save(dialog);
    }

    @Transactional
    public void delete(Dialog dialog) {
        dialogRepository.delete(dialog);
    }
}
