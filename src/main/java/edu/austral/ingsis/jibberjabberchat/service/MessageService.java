package edu.austral.ingsis.jibberjabberchat.service;

import edu.austral.ingsis.jibberjabberchat.domain.Message;
import edu.austral.ingsis.jibberjabberchat.domain.MessageStatus;
import edu.austral.ingsis.jibberjabberchat.dto.NewMessageDto;
import edu.austral.ingsis.jibberjabberchat.exception.NotFoundException;
import edu.austral.ingsis.jibberjabberchat.factory.MessageFactory;
import edu.austral.ingsis.jibberjabberchat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MessageService {
    private final MessageRepository repository;
    private final RoomService roomService;

    @Autowired
    public MessageService(MessageRepository repository, RoomService roomService) {
        this.repository = repository;
        this.roomService = roomService;
    }

    public Message save(NewMessageDto messageDto, String chatId){
        return repository.save(MessageFactory.save(messageDto, chatId, MessageStatus.RECEIVED));
    }

    public Message getChatMessage(Long messageId){
        return repository.findById(messageId).orElseThrow(() -> new NotFoundException("Message not found"));
    }

    public Set<Message> findMessage(String senderId, String receiverId){
        String chatId = roomService.getChatId(senderId, receiverId, false);
        return new HashSet<>(repository.findAllByChatId(chatId));
    }
}
