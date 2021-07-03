package edu.austral.ingsis.jibberjabberchat.repository;

import edu.austral.ingsis.jibberjabberchat.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findBySenderIdAndReceiverId(String senderId, String receiver);
}
