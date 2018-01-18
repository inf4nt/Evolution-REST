package evolution.repository;

import evolution.model.channel.Channel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    @Async
    @Query("select c from Channel c where c.id =:id")
    CompletableFuture<Channel> findOneAsync(@Param("id") Long id);

    @Query("select c " +
            " from Channel c " +
            " join fetch c.channelUser cu " +
            " where cu.id =:userId ")
    List<Channel> findChannelForChannelUser(@Param("userId") Long userId);

    @Query("select c " +
            " from Channel c " +
            " join fetch c.channelUser cu " +
            " where cu.id =:userId ")
    List<Channel> findChannelForChannelUser(@Param("userId") Long userId, Sort sort);

    @Query(value = "select c " +
            " from Channel c " +
            " join fetch c.channelUser cu " +
            " where cu.id =:userId ", countQuery = "select count (c.id) from Channel c")
    Page<Channel> findChannelForChannelUser(@Param("userId") Long userId, Pageable pageable);

    @Query("select c from Channel c where c.channelName =:name")
    List<Channel> findChannelByName(@Param("name") String name);

    @Query("select c from Channel c where c.channelName =:name")
    List<Channel> findChannelByName(@Param("name") String name, Sort sort);

    @Query("select c from Channel c where c.channelName =:name")
    Page<Channel> findChannelByName(@Param("name") String name, Pageable pageable);

    @Query("select c from Channel c where c.id =:id")
    Optional<Channel> findOneChannel(@Param("id") Long id);

    @Query(" select c " +
            " from Channel c " +
            " join fetch c.channelUser cu " +
            " where c.id =:id ")
    Optional<Channel> findChannelByIdLazyChannelUser(@Param("id") Long id);

    @Query("select c " +
            " from Channel c " +
            " where c.whoCreatedChannel.id =:userId ")
    List<Channel> findChannelForWhoCreateChannelUser(@Param("userId") Long userId);

    @Query("select c " +
            " from Channel c " +
            " where c.whoCreatedChannel.id =:userId ")
    List<Channel> findChannelForWhoCreateChannelUser(@Param("userId") Long userId, Sort sort);

    @Query(value = "select c " +
            " from Channel c " +
            " where c.whoCreatedChannel.id =:userId ", countQuery = "select count (c.id) from Channel c")
    Page<Channel> findChannelForWhoCreateChannelUser(@Param("userId") Long userId, Pageable pageable);

    @Query("select count (c.pk.user.id) from ChannelUserReference c where c.pk.channel.id =:id")
    Long countUserByChannel(@Param("id") Long id);

    @Async
    @Query(" select c from " +
            " Channel c " +
            " join fetch c.channelUser cu " +
            " where cu.id =:userid or c.whoCreatedChannel.id =:userid ")
    CompletableFuture<List<Channel>> findMyChannelAsync(@Param("userid") Long userid);
}
