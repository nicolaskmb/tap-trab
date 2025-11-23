package br.univille.chat.infrastructure.persistence;

import br.univille.chat.domain.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataConversation extends JpaRepository<Conversation, UUID> {
    
    @Query("SELECT c FROM Conversation c WHERE (c.participantA = :u1 AND c.participantB = :u2) OR (c.participantA = :u2 AND c.participantB = :u1)")
    Optional<Conversation> findExisting(UUID u1, UUID u2);

    @Query("SELECT c FROM Conversation c WHERE c.participantA = :u OR c.participantB = :u")
    List<Conversation> findByUser(UUID u);
}