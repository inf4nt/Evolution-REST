package evolution.repository;

import evolution.model.channel.ChannelUserReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


public interface ChannelUserReferenceRepository extends JpaRepository<ChannelUserReference, ChannelUserReference.ChannelUserReferenceEmbeddable> {

    @Query("select c from ChannelUserReference c where c.pk.user.id =:userid and c.pk.channel.id =:channelid")
    Optional<ChannelUserReference> findByUserAndChannel(@Param("channelid") Long channelId, @Param("userid") Long userId);

    @Async
    @Query("select c from ChannelUserReference c where c.pk.user.id =:userid and c.pk.channel.whoCreatedChannel.id <>:userid")
    CompletableFuture<List<ChannelUserReference>> findByUserAndNotWhoCreateChannelAsync(@Param("userid") Long userId);

    @Query("select c from ChannelUserReference c where c.pk.user.id =:userid and c.pk.channel.whoCreatedChannel.id <>:userid")
    List<ChannelUserReference> findByUserAndNotWhoCreateChannel(@Param("userid") Long userId);
}
