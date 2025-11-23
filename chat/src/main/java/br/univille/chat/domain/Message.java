package br.univille.chat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID senderId;
    
    @Column(nullable = false)
    private String content;
    
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    public Message(UUID senderId, String content, Conversation conversation) {
        this.senderId = senderId;
        this.content = content;
        this.conversation = conversation;
        this.timestamp = LocalDateTime.now();
    }
}