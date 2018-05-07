package evolution.repository;

import evolution.model.MessagePrivate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "message_ev", path = "message_ev")
public interface MessagePrivateRepository extends JpaRepository<MessagePrivate, Long> {

    @Query("select m from MessagePrivate m where m.sender.id =:sender and m.recipient.id =:recipient")
    List<MessagePrivate> findAllBySenderAndRecipient(@Param("sender") Long sender, @Param("recipient") Long recipient);

    @Query("select m from MessagePrivate m where m.id =:id and (m.sender.id =:userId or m.recipient.id =:userId)")
    Optional<MessagePrivate> findMessageByIdAndSenderOrRecipient(@Param("id") Long id, @Param("userId") Long userId);
}
