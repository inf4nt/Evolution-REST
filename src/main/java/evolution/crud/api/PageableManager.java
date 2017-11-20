package evolution.crud.api;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Infant on 07.11.2017.
 */
public interface PageableManager {

    default Pageable getPageableForRestService(Integer page, Integer size,
                                               Integer defaultMaxFetch) {
        Integer pageResult = 0;
        Integer sizeResult = 0;

        if (page == null || size == null) {
            sizeResult = defaultMaxFetch;
        }

        return new PageRequest(pageResult, sizeResult);
    }

    default Pageable getPageableForRestService(Integer page, Integer size, String sortType, List<String> sortProperties,
                                               Integer defaultMaxFetch, String defaultSortType, String defaultSortProperties) {

        Integer pageResult = page;
        Integer sizeResult = size;
        String sortTypeResult = sortType;
        List<String> sortPropertiesResult = sortProperties;

        if (page == null || size == null) {
            sizeResult = defaultMaxFetch;
            pageResult = 0;
        }

        if (sortTypeResult == null || sortTypeResult.isEmpty()) {
            sortTypeResult = defaultSortType.toUpperCase();
        } else {
            sortTypeResult = sortTypeResult.toUpperCase();
        }

        if (sortPropertiesResult == null || sortPropertiesResult.isEmpty()) {
            String arr[] = defaultSortProperties.split(",");

            sortPropertiesResult = Arrays.stream(arr)
                    .map(o -> o.trim())
                    .collect(Collectors.toList());
        }

        return new PageRequest(pageResult, sizeResult, new Sort(Sort.Direction.valueOf(sortTypeResult), sortPropertiesResult));
    }

    default Sort getSortForRestService(String sortType, List<String> sortProperties,
                                       String defaultSortType, String defaultSortProperties) {

        String sortTypeResult = sortType;
        List<String> sortPropertiesResult = sortProperties;

        if (sortTypeResult == null || sortTypeResult.isEmpty()) {
            sortTypeResult = defaultSortType.toUpperCase();
        } else {
            sortTypeResult = sortType.toUpperCase();
        }

        if (sortPropertiesResult == null || sortPropertiesResult.isEmpty()) {
            String arr[] = defaultSortProperties.split(",");

            sortPropertiesResult = Arrays.stream(arr)
                    .map(o -> o.trim())
                    .collect(Collectors.toList());
        }

        return new Sort(Sort.Direction.valueOf(sortTypeResult), sortPropertiesResult);
    }

}

