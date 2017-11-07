package evolution.crud;

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

    Pageable getPageable(Integer page, Integer size, String sort, List<String> sortProperties);

    Sort getSort(String sort, List<String> sortProperties);

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

        if (sortType == null || sortType.isEmpty()) {
            sortTypeResult = defaultSortType.toUpperCase();
        } else {
            sortTypeResult = sortTypeResult.toUpperCase();
        }

        if (sortProperties == null || !sortProperties.isEmpty()) {
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

        if (sortType == null || sortType.isEmpty()) {
            sortTypeResult = defaultSortType.toUpperCase();
        }

        if (sortProperties == null || !sortProperties.isEmpty()) {
            String arr[] = defaultSortProperties.split(",");

            sortPropertiesResult = Arrays.stream(arr)
                    .map(o -> o.trim())
                    .collect(Collectors.toList());
        }

        return new Sort(Sort.Direction.valueOf(sortTypeResult), sortPropertiesResult);
    }

}
