package evolution.manager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface AbstracrApiCrudService <DTO, Pk> {

    Optional<DTO> findOne(Pk pk);

    default CompletableFuture<Optional<DTO>> findOneAsync(Pk pk) {
        return CompletableFuture.supplyAsync(() -> findOne(pk));
    }

    List<DTO> findAll();

    Page<DTO> findAll(Pageable pageable);

    default CompletableFuture<List<DTO>> findAllAsync() {
        return CompletableFuture.supplyAsync(this::findAll);
    }

    default CompletableFuture<Page<DTO>> findAllAsync(Pageable pageable) {
        return CompletableFuture.supplyAsync(() -> findAll(pageable));
    }

    void delete(DTO dto);

    void deleteById(Pk pk);
}
