package evolution.service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Infant on 15.07.2017.
 */
public class CollectionUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionUtils.class);

    public static <E> List<List<E>> split(Collection<E> collection, int capacity) {
        List<List<E>> result = new ArrayList<>();
        List<E> demo = new ArrayList<>();
        Iterator<E> iterator = collection.iterator();
        int step = 1;
        int s = 0;
        while (iterator.hasNext()) {
            demo.add(iterator.next());
            if (s == collection.size() - 1) {
                result.add(demo);
                break;
            }
            s++;
            if (step == capacity) {
                result.add(demo);
                demo = new ArrayList<>();
                step = 1;
                continue;
            }
            step ++;
        }
        return result;
    }

}
