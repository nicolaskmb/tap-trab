package br.univille.chat.infrastructure.persistence;

import br.univille.chat.domain.ChatRepository;
import br.univille.chat.domain.Conversation;
import br.univille.chat.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaChatRepository implements ChatRepository {
    private final SpringDataConversation conversationRepo;
    private final SpringDataMessage messageRepo;

    @Override
    public Conversation save(Conversation conversation) {
        return conversationRepo.save(conversation);
    }

    @Override
    public Optional<Conversation> findById(UUID id) {
        return conversationRepo.findById(id);
    }

    @Override
    public Optional<Conversation> findByParticipants(UUID user1, UUID user2) {
        return conversationRepo.findExisting(user1, user2);
    }

    @Override
    public List<Conversation> findByParticipant(UUID user) {
        return conversationRepo.findByUser(user);
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepo.save(message);
    }
}