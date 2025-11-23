package br.univille.chat.infrastructure;

import br.univille.chat.domain.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, UUID> {
    
    @Query("SELECT c FROM Conversation c WHERE (c.participantA = :user1 AND c.participantB = :user2) OR (c.participantA = :user2 AND c.participantB = :user1)")
    Optional<Conversation> findExistingConversation(UUID user1, UUID user2);

    @Query("SELECT c FROM Conversation c WHERE c.participantA = :user OR c.participantB = :user")
    List<Conversation> findByUser(UUID user);
}