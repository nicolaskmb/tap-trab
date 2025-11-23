package br.univille.chat.interfaces.rest.dto;

import java.util.UUID;

public record StartChatRequest(UUID targetUserId) {
}