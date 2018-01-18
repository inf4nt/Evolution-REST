package evolution.crud;

import evolution.crud.api.DialogCrudManagerService;
import evolution.model.Dialog;
import evolution.model.Message;
import evolution.repository.DialogRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Infant on 07.11.2017.
 */
@Service
public class DialogCrudManagerServiceImpl implements DialogCrudManagerService {

    private final DialogRepository dialogRepository;

    @Value("${model.dialog.maxfetch}")
    private Integer dialogMaxFetch;

    @Value("${model.dialog.defaultsort}")
    private String defaultDialogSortType;

    @Value("${model.dialog.defaultsortproperties}")
    private String defaultDialogSortProperties;

    @Autowired
    public DialogCrudManagerServiceImpl(DialogRepository dialogRepository) {
        this.dialogRepository = dialogRepository;
    }

    @Override
    public List<Dialog> findAll() {
        return dialogRepository.findAll();
    }

    @Override
    public Optional<Dialog> findOneLazy(Long id) {
        return dialogRepository.findOneLazy(id);
    }

    @Override
    public CompletableFuture<Optional<Dialog>> findDialogByUsersAsync(Long first, Long second) {
        return dialogRepository.findDialogByUsersAsync(first, second)
                .thenApply(v -> Optional.ofNullable(v));
    }

    @Override
    public Optional<Dialog> findOneLazyAndParticipantId(Long id, Long participant) {
        return dialogRepository.findOneLazyAndParticipantId(id, participant);
    }

    @Override
    public List<Dialog> findAllLazy() {
        return dialogRepository.findAllLazy();
    }

    @Override
    public List<Dialog> findAllLazy(String sort, List<String> sortProperties) {
        Sort s = getSortForRestService(sort, sortProperties,
                this.defaultDialogSortType, this.defaultDialogSortProperties);
        return dialogRepository.findAllLazy(s);
    }

    @Override
    public Page<Dialog> findAllLazy(Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable pageable = getPageableForRestService(page, size, sort, sortProperties,
                this.dialogMaxFetch, this.defaultDialogSortType, this.defaultDialogSortProperties);
        return dialogRepository.findAllLazy(pageable);
    }

    @Override
    public List<Dialog> findAll(String sort, List<String> sortProperties) {
        Sort s = getSortForRestService(sort, sortProperties,
                this.defaultDialogSortType, this.defaultDialogSortProperties);
        return dialogRepository.findAll(s);
    }

    @Override
    public Page<Dialog> findAll(Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable pageable = getPageableForRestService(page, size, sort, sortProperties,
                this.dialogMaxFetch, this.defaultDialogSortType, this.defaultDialogSortProperties);
        return dialogRepository.findAll(pageable);
    }

    @Override
    public Page<Dialog> findMyDialog(Long iam, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable pageable = getPageableForRestService(page, size, sort, sortProperties,
                this.dialogMaxFetch, this.defaultDialogSortType, this.defaultDialogSortProperties);
        return dialogRepository.findMyDialog(iam, pageable);
    }

    @Override
    public List<Dialog> findMyDialog(Long iam, String sort, List<String> sortProperties) {
        Sort s = getSortForRestService(sort, sortProperties,
                this.defaultDialogSortType, this.defaultDialogSortProperties);
        return dialogRepository.findMyDialog(iam, s);
    }

    @Override
    public List<Dialog> findDialogsByUserId(Long userId) {
        return dialogRepository.findMyDialog(userId);
    }

    @Override
    public List<Dialog> findDialogsByUserId(Long userId, String sort, List<String> sortProperties) {
        Sort s = getSortForRestService(sort, sortProperties,
                this.defaultDialogSortType, this.defaultDialogSortProperties);
        return dialogRepository.findMyDialog(userId, s);
    }

    @Override
    public Page<Dialog> findDialogsByUserId(Long userId, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable pageable = getPageableForRestService(page, size, sort, sortProperties,
                this.dialogMaxFetch, this.defaultDialogSortType, this.defaultDialogSortProperties);
        return dialogRepository.findMyDialog(userId, pageable);
    }

    @Override
    public Optional<Dialog> findOne(Long iam, Long dialogId) {
        return dialogRepository.findOneDialog(iam, dialogId);
    }

    @Override
    public void delete(Dialog dialog) {
        dialogRepository.delete(dialog);
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        ids.forEach(o -> delete(o));
    }

    @Override
    public Optional<Dialog> findOne(Long aLong) {
        return dialogRepository.findOneDialog(aLong);
    }

    @Override
    public Dialog save(Dialog object) {
        return dialogRepository.save(object);
    }

    @Override
    public void delete(Long aLong) {
        Optional<Dialog> optional = findOne(aLong);
        optional.ifPresent(dialog -> dialogRepository.delete(dialog));
    }

    @Override
    @Transactional
    public void clearRowByUserForeignKey(Long id) {
        List<Dialog> list = dialogRepository.findMyDialog(id);
        list.forEach(o -> Hibernate.initialize(o.getMessageList().size()));
        dialogRepository.delete(list);
    }

    @Override
    public CompletableFuture<List<Dialog>> findMyDialogAsync(Long userId) {
        return dialogRepository.findMyDialogAsync(userId);
    }

    @Override
    public void deleteList(List<Dialog> list) {
        dialogRepository.delete(list);
    }

    @Override
    @Transactional
    public Dialog initializeLazy(Dialog dialog) {
        Hibernate.initialize(dialog.getMessageList().size());
        return dialog;
    }
}
