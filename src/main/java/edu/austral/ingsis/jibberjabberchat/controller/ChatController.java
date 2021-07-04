package edu.austral.ingsis.jibberjabberchat.controller;

import edu.austral.ingsis.jibberjabberchat.domain.Message;
import edu.austral.ingsis.jibberjabberchat.domain.Room;
import edu.austral.ingsis.jibberjabberchat.dto.NewMessageDto;
import edu.austral.ingsis.jibberjabberchat.service.MessageService;
import edu.austral.ingsis.jibberjabberchat.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final RoomService roomService;

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate, MessageService messageService, RoomService roomService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
        this.roomService = roomService;
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload NewMessageDto newMessage){
        String chatId = roomService.getChatId(newMessage.getSenderId(), newMessage.getReceiverId(), true);
        Message message = messageService.save(newMessage, chatId);
        messagingTemplate.convertAndSendToUser(message.getReceiverId(), "/queue/messages", message);
        messagingTemplate.convertAndSendToUser(message.getSenderId(), "/queue/messages", message);
    }

    @MessageMapping("/read")
    public void processReadChat(@Payload Long messageId){
        Message message = messageService.getChatMessage(messageId);
        messageService.markMessageAsRead(message.getId());
        Message saved = messageService.getChatMessage(messageId);
        messagingTemplate.convertAndSendToUser(message.getReceiverId(), "/queue/read", saved);
        messagingTemplate.convertAndSendToUser(message.getSenderId(), "/queue/read", saved);
    }
}
