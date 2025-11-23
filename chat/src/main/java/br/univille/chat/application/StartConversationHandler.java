package br.univille.chat.application;

import br.univille.chat.domain.ChatRepository;
import br.univille.chat.domain.Conversation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StartConversationHandler {
    private final ChatRepository repository;

    public Conversation handle(UUID senderId, UUID targetId) {
        if (senderId.equals(targetId)) throw new IllegalArgumentException("Não pode conversar consigo mesmo");

        return repository.findByParticipants(senderId, targetId)
                .orElseGet(() -> repository.save(new Conversation(senderId, targetId)));
    }
}