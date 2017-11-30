package evolution.rest.api;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AbstractRestService {

    default <T> ResponseEntity<Page<T>> response(Page<T> page) {
        if (page == null || page.getContent() == null || page.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(page);
    }

    default <T> ResponseEntity<List<T>> response(List<T> list) {
        if (list == null || list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    default <T> ResponseEntity<T> response(Optional<T> optional) {
        if (optional == null || !optional.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(optional.get());
    }
}
