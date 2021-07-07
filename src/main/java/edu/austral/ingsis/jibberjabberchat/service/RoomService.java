package edu.austral.ingsis.jibberjabberchat.service;

import edu.austral.ingsis.jibberjabberchat.domain.Room;
import edu.austral.ingsis.jibberjabberchat.exception.NotFoundException;
import edu.austral.ingsis.jibberjabberchat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public String getChatId(String senderId, String receiverId, Boolean creteIfNotExist){
        if (!this.roomRepository.existsRoomBySenderIdAndReceiverId(senderId, receiverId) && creteIfNotExist){
            String chatId = senderId + "_" + receiverId;
            roomRepository.save(Room.builder().chatId(chatId).senderId(senderId).receiverId(receiverId).build());
            roomRepository.save(Room.builder().chatId(chatId).senderId(receiverId).receiverId(senderId).build());
            return chatId;
        }
        return roomRepository.findBySenderIdAndReceiverId(senderId, receiverId).orElseThrow(() -> new NotFoundException("Chat not exist")).getChatId();
    }


    public List<Room> getAllRooms(String userId){
        return this.roomRepository.findAllBySenderId(userId);
    }

    public Room getChat(String senderId, String receiverId){
        return this.roomRepository.findBySenderIdAndReceiverId(senderId, receiverId).orElseThrow(() -> new NotFoundException("Chat not exist"));
    }

}
