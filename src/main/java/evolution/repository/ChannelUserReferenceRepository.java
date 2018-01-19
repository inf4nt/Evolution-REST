package evolution.repository;

import evolution.model.channel.ChannelUserReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ChannelUserReferenceRepository extends JpaRepository<ChannelUserReference, ChannelUserReference.ChannelUserReferenceEmbeddable> {

    @Query("select c from ChannelUserReference c where c.pk.user.id =:userid and c.pk.channel.id =:channelid")
    Optional<ChannelUserReference> findByUserAndChannel(@Param("channelid") Long channelId, @Param("userid") Long userId);
}
