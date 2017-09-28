package evolution.dao;

import evolution.model.feed.Like;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Infant on 27.07.2017.
 */
public interface LikeRepository extends JpaRepository<Like, Long> {
}
