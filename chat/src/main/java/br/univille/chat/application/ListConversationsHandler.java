package br.univille.chat.application;

import br.univille.chat.domain.ChatRepository;
import br.univille.chat.domain.Conversation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListConversationsHandler {
    private final ChatRepository repository;

    public List<Conversation> handle(UUID userId) {
        return repository.findByParticipant(userId);
    }
}