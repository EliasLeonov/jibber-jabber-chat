package edu.austral.ingsis.jibberjabberchat.service;

import edu.austral.ingsis.jibberjabberchat.domain.Room;
import edu.austral.ingsis.jibberjabberchat.exception.NotFoundException;
import edu.austral.ingsis.jibberjabberchat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public String getChatId(String senderId, String receiverId, Boolean creteIfNotExist){
        if (creteIfNotExist){
            String chatId = senderId + receiverId;
            roomRepository.save(Room.builder().chatId(chatId).senderId(senderId).receiverId(receiverId).build());
            roomRepository.save(Room.builder().chatId(chatId).senderId(receiverId).receiverId(senderId).build());
            return chatId;
        }
        return roomRepository.findBySenderIdAndReceiverId(senderId, receiverId).orElseThrow(() -> new NotFoundException("Chat not exist")).getChatId();
    }


    public Set<Room> getAllRooms(String userId){
        return this.roomRepository.findAllBySenderId(userId);
    }

}
