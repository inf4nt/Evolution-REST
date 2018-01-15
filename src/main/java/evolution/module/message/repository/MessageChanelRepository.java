package evolution.module.message.repository;

import evolution.module.message.model.MessageChannel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageChanelRepository extends JpaRepository<MessageChannel, Long> {

    @Query("select m from MessageChannel m where m.id =:id")
    Optional<MessageChannel> findOneMessageChannel(@Param("id") Long id);

    @Query("select m from MessageChannel m where m.id =:id and m.sender.id =:senderId")
    Optional<MessageChannel> findOneMessageChannel(@Param("id") Long id, @Param("senderId") Long senderId);

    @Query("select m from MessageChannel m where m.channel.id =:id order by m.id asc")
    List<MessageChannel> findMessageChannelByChannelId(@Param("id") Long id);

    @Query("select m from MessageChannel m where m.channel.id =:id")
    List<MessageChannel> findMessageChannelByChannelId(@Param("id") Long id, Sort sort);

    @Query("select m from MessageChannel m where m.channel.id =:id")
    Page<MessageChannel> findMessageChannelByChannelId(@Param("id") Long id, Pageable pageable);

    @Query("select m from MessageChannel m where m.sender.id =:sender")
    List<MessageChannel> findMessageChannelBySender(@Param("sender") Long senderId);

    @Query("select m from MessageChannel m where m.sender.id =:sender")
    List<MessageChannel> findMessageChannelBySender(@Param("sender") Long senderId, Sort sort);

    @Query("select m from MessageChannel m where m.sender.id =:sender")
    Page<MessageChannel> findMessageChannelBySender(@Param("sender") Long senderId, Pageable pageable);

    @Query("select count (m.id) from MessageChannel m where m.channel.id =:id")
    Long findCountMessageChannelByChannelId(@Param("id") Long id);

    @Query("select count (m.id) from MessageChannel m where m.channel.channelName =:name")
    Long findCountMessageChannelByChannelName(@Param("name") String name);

}
