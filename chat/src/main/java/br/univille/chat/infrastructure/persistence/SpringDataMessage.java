package br.univille.chat.infrastructure.persistence;

import br.univille.chat.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataMessage extends JpaRepository<Message, UUID> {}