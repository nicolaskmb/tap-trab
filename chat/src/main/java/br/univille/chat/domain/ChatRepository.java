package br.univille.chat.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRepository {
    Conversation save(Conversation conversation);
    Optional<Conversation> findById(UUID id);
    Optional<Conversation> findByParticipants(UUID user1, UUID user2);
    List<Conversation> findByParticipant(UUID user);
    Message saveMessage(Message message);
}