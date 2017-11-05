package evolution.helper;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 28.10.2017.
 */
@Service
public class HelperRestService<T> {

    public ResponseEntity<Page<T>> getResponseForPage(Page<T> page) {
        if (page == null || page.getContent() == null || page.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(page);
    }

    public ResponseEntity<List<T>> getResponseForList(List<T> list) {
        if (list == null ||  list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    public ResponseEntity<T> getResponseForOptional(Optional<T> optional) {
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        }
        return ResponseEntity.noContent().build();
    }
}
