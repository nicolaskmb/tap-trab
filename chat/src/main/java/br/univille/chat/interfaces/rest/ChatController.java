package br.univille.chat.interfaces.rest;

import br.univille.chat.application.*;
import br.univille.chat.domain.Conversation;
import br.univille.chat.domain.Message;
import br.univille.chat.interfaces.rest.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final StartConversationHandler startHandler;
    private final SendMessageHandler sendHandler;
    private final ListConversationsHandler listHandler;
    private final br.univille.chat.domain.ChatRepository repository;

    @PostMapping("/conversations")
    public ResponseEntity<ConversationResponse> start(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody StartChatRequest request) {
        
        Conversation c = startHandler.handle(UUID.fromString(userId), request.targetUserId());
        return ResponseEntity.ok(new ConversationResponse(c.getId(), request.targetUserId()));
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationResponse>> list(@RequestHeader("X-User-Id") String userId) {
        UUID uid = UUID.fromString(userId);
        List<Conversation> list = listHandler.handle(uid);
        
        List<ConversationResponse> resp = list.stream().map(c -> {
            UUID other = c.getParticipantA().equals(uid) ? c.getParticipantB() : c.getParticipantA();
            return new ConversationResponse(c.getId(), other);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(resp);
    }

    @PostMapping("/conversations/{chatId}/messages")
    public ResponseEntity<MessageResponse> send(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable UUID chatId,
            @RequestBody MessageRequest request) {
        
        Message m = sendHandler.handle(UUID.fromString(userId), chatId, request.content());
        return ResponseEntity.ok(new MessageResponse(m.getId(), m.getSenderId(), m.getContent(), m.getTimestamp()));
    }

    @GetMapping("/conversations/{chatId}/messages")
    public ResponseEntity<List<MessageResponse>> getMessages(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable UUID chatId) {
        
        Conversation c = repository.findById(chatId).orElseThrow();
        UUID uid = UUID.fromString(userId);
        if(!c.getParticipantA().equals(uid) && !c.getParticipantB().equals(uid)) return ResponseEntity.status(403).build();

        List<MessageResponse> resp = c.getMessages().stream()
            .map(m -> new MessageResponse(m.getId(), m.getSenderId(), m.getContent(), m.getTimestamp()))
            .collect(Collectors.toList());
            
        return ResponseEntity.ok(resp);
    }
}