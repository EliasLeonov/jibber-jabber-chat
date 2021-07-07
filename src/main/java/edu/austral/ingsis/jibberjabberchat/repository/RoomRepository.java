package edu.austral.ingsis.jibberjabberchat.repository;

import edu.austral.ingsis.jibberjabberchat.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findBySenderIdAndReceiverId(String senderId, String receiver);
    List<Room> findAllBySenderId(String senderId);
    Optional<Room> findByChatId(String chatId);
    boolean existsRoomBySenderIdAndReceiverId(String senderId, String receiver);
}
