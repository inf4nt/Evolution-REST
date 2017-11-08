package evolution.business;

import evolution.common.BusinessServiceExecuteStatus;
import lombok.Getter;

import java.util.Collection;
import java.util.Optional;

import static evolution.common.BusinessServiceExecuteStatus.*;

/**
 * Created by Infant on 09.11.2017.
 */
public class BusinessServiceExecuteResult<T> {

    @Getter
    private BusinessServiceExecuteStatus executeStatus;

    @Getter
    private Optional<T> resultObject;

    private BusinessServiceExecuteResult(BusinessServiceExecuteStatus executeStatus, T resultObject) {
        this.executeStatus = executeStatus;
        this.resultObject = Optional.ofNullable(resultObject);
    }

    private BusinessServiceExecuteResult(BusinessServiceExecuteStatus executeStatus, Optional<T> resultObject) {
        this.executeStatus = executeStatus;
        this.resultObject = resultObject;
    }

    private BusinessServiceExecuteResult(BusinessServiceExecuteStatus executeStatus) {
        this.executeStatus = executeStatus;
    }

    public static <T> BusinessServiceExecuteResult<T> build(BusinessServiceExecuteStatus status) {
        return new BusinessServiceExecuteResult<>(status);
    }

    public static <T> BusinessServiceExecuteResult<T> build(BusinessServiceExecuteStatus status, T resultObject) {
        return new BusinessServiceExecuteResult<>(status, resultObject);
    }

    public static <T> BusinessServiceExecuteResult<T> build(BusinessServiceExecuteStatus status, Optional<T> resultObject) {
        return new BusinessServiceExecuteResult<>(status, resultObject);
    }

    public static <T> BusinessServiceExecuteResult<T> build(Optional<T> resultObject) {
        if (resultObject == null) {
            return new BusinessServiceExecuteResult<>(NO_CONTENT, Optional.empty());
        }
        if (resultObject.isPresent()) {
            if (resultObject.get() instanceof Collection && ((Collection) resultObject.get()).isEmpty()) {
                return new BusinessServiceExecuteResult<>(NO_CONTENT, resultObject);
            }
            return new BusinessServiceExecuteResult<>(OK, resultObject);
        } else {
            return new BusinessServiceExecuteResult<>(NO_CONTENT, resultObject);
        }
    }
}
