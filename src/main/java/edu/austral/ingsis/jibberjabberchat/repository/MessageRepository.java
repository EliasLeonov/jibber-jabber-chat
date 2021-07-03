package edu.austral.ingsis.jibberjabberchat.repository;

import edu.austral.ingsis.jibberjabberchat.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatId(String chatId);
}
