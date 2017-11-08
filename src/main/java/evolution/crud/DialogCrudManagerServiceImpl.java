package evolution.crud;

import evolution.crud.api.DialogCrudManagerService;
import evolution.model.Dialog;
import evolution.repository.DialogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 07.11.2017.
 */
@Service
public class DialogCrudManagerServiceImpl implements DialogCrudManagerService {

    private final DialogRepository dialogRepository;

    @Autowired
    public DialogCrudManagerServiceImpl(DialogRepository dialogRepository) {
        this.dialogRepository = dialogRepository;
    }

    @Override
    public List<Dialog> findAll() {
        return dialogRepository.findAll();
    }

    @Override
    public List<Dialog> findAll(String sort, List<String> sortProperties) {
        Sort s = getSort(sort, sortProperties);
        return dialogRepository.findAll(s);
    }

    @Override
    public Page<Dialog> findAll(Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable pageable = getPageable(page, size, sort, sortProperties);
        return dialogRepository.findAll(pageable);
    }

    @Override
    public Page<Dialog> findMyDialog(Long iam, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable pageable = getPageable(page, size, sort, sortProperties);
        return dialogRepository.findMyDialog(iam, pageable);
    }

    @Override
    public List<Dialog> findMyDialog(Long iam, String sort, List<String> sortProperties) {
        Sort s = getSort(sort, sortProperties);
        return dialogRepository.findMyDialog(iam, s);
    }

    @Override
    public Optional<Dialog> findOne(Long iam, Long dialogId) {
        return dialogRepository.findOneDialog(iam, dialogId);
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

    @Value("${model.dialog.maxfetch}")
    private Integer dialogMaxFetch;

    @Value("${model.dialog.defaultsort}")
    private String defaultDialogSortType;

    @Value("${model.dialog.defaultsortproperties}")
    private String defaultDialogSortProperties;

    @Override
    public Pageable getPageable(Integer page, Integer size, String sort, List<String> sortProperties) {
        return getPageableForRestService(page, size, sort, sortProperties,
                this.dialogMaxFetch, this.defaultDialogSortType, this.defaultDialogSortProperties);
    }

    @Override
    public Pageable getPageable(Integer page, Integer size) {
        return getPageableForRestService(page, size,
                this.dialogMaxFetch);
    }

    @Override
    public Sort getSort(String sort, List<String> sortProperties) {
        return getSortForRestService(sort, sortProperties,
                this.defaultDialogSortType, this.defaultDialogSortProperties);
    }
}
