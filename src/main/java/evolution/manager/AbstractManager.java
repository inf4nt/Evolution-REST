package evolution.manager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface AbstractManager<Obj, Pk> {

    Optional<Obj> findOne(Pk pk);

    default CompletableFuture<Optional<Obj>> findOneAsync(Pk pk) {
        return CompletableFuture.supplyAsync(() -> findOne(pk));
    }

    List<Obj> findAll();

    Page<Obj> findAll(Pageable pageable);

    default CompletableFuture<List<Obj>> findAllAsync() {
        return CompletableFuture.supplyAsync(this::findAll);
    }

    default CompletableFuture<Page<Obj>> findAllAsync(Pageable pageable) {
        return CompletableFuture.supplyAsync(() -> findAll(pageable));
    }

    void delete(Obj obj);

    void deleteById(Pk pk);

    Obj save(Obj obj);

    default CompletableFuture<Obj> saveAsync(Obj obj) {
        return CompletableFuture.supplyAsync(() -> save(obj));
    }
}
