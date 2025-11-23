package br.univille.chat.interfaces.rest.dto;

import java.util.UUID;

public record ConversationResponse(UUID id, UUID otherParticipantId) {
}