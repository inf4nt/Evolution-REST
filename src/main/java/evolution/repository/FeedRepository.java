package evolution.repository;

import evolution.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Infant on 08.11.2017.
 */
public interface FeedRepository extends JpaRepository<Feed, Long> {
}
