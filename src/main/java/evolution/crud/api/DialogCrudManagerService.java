package evolution.crud.api;

import evolution.model.Dialog;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Infant on 07.11.2017.
 */
public interface DialogCrudManagerService extends AbstractCrudManagerService<Dialog, Long>, PageableManager, InitializeLazyCrudManagerService<Dialog> {

    Optional<Dialog> findOneLazy(Long id);

    CompletableFuture<Optional<Dialog>> findDialogByUsersAsync(Long first, Long second);

    Optional<Dialog> findOneLazyAndParticipantId(Long id, Long participant);

    List<Dialog> findAllLazy();

    List<Dialog> findAllLazy(String sort, List<String> sortProperties);

    Page<Dialog> findAllLazy(Integer page, Integer size, String sort, List<String> sortProperties);

    List<Dialog> findMyDialog(Long iam);

    Page<Dialog> findMyDialog(Long iam, Integer page, Integer size, String sort, List<String> sortProperties);

    List<Dialog> findMyDialog(Long iam, String sort, List<String> sortProperties);

    List<Dialog> findDialogsByUserId(Long userId);

    List<Dialog> findDialogsByUserId(Long userId, String sort, List<String> sortProperties);

    Page<Dialog> findDialogsByUserId(Long userId, Integer page, Integer size, String sort, List<String> sortProperties);

    Optional<Dialog> findOne(Long iam, Long dialogId);

    void delete(Dialog dialog);

    void delete(List<Long> ids);

    CompletableFuture<Void> deleteIfNotHaveMessageAsync(Long id);

    void clearRowByUserForeignKey(Long id);

    CompletableFuture<List<Dialog>> findMyDialogAsync(Long userId);

    void deleteList(List<Dialog> list);
}
