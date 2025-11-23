package br.univille.chat.application;

import br.univille.chat.domain.ChatRepository;
import br.univille.chat.domain.Conversation;
import br.univille.chat.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SendMessageHandler {
    private final ChatRepository repository;

    public Message handle(UUID senderId, UUID conversationId, String content) {
        Conversation conversation = repository.findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("Conversa não encontrada"));

        if (!conversation.getParticipantA().equals(senderId) && !conversation.getParticipantB().equals(senderId)) {
            throw new IllegalArgumentException("Você não participa desta conversa");
        }

        Message message = new Message(senderId, content, conversation);
        return repository.saveMessage(message);
    }
}