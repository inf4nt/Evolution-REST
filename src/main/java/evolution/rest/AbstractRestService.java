package evolution.rest;

import evolution.business.BusinessServiceExecuteResult;
import evolution.common.BusinessServiceExecuteStatus;
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
        if (!optional.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(optional.get());
    }

    default <T> ResponseEntity<T> response(BusinessServiceExecuteResult<T> executeResult) {
        if (executeResult.getExecuteStatus() == BusinessServiceExecuteStatus.OK && executeResult.getResultObjectOptional().isPresent()) {
            return ResponseEntity.ok(executeResult.getResultObject());
        } else if (executeResult.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return ResponseEntity.ok().build();
        } else if (executeResult.getExecuteStatus() == BusinessServiceExecuteStatus.FORBIDDEN) {
            return ResponseEntity.status(403).build();
        } else if (executeResult.getExecuteStatus() == BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE) {
            return ResponseEntity.noContent().build();
        } else if (executeResult.getExecuteStatus() == BusinessServiceExecuteStatus.NOT_FOUND_PRINCIPAL_FOR_EXECUTE) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.status(417).build();
    }
}
