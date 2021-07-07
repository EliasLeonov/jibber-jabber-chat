package edu.austral.ingsis.jibberjabberchat.controller;

import edu.austral.ingsis.jibberjabberchat.domain.ChatNotification;
import edu.austral.ingsis.jibberjabberchat.domain.Message;
import edu.austral.ingsis.jibberjabberchat.domain.Room;
import edu.austral.ingsis.jibberjabberchat.dto.ChatReadDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Controller
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

        final Room receiverChat = this.roomService.getChat(newMessage.getReceiverId(), newMessage.getSenderId());
        final long receiverUnreadCount = messageService.getUnReadCount(chatId, message.getReceiverId());
        receiverChat.setUnreadCount(receiverUnreadCount);
        messagingTemplate.convertAndSendToUser(message.getReceiverId(), "/queue/messages", ChatNotification.builder().message(message).chat(receiverChat).build());

        final Room senderChat = this.roomService.getChat(newMessage.getSenderId(), newMessage.getReceiverId());
        senderChat.setUnreadCount(0);
        messagingTemplate.convertAndSendToUser(message.getSenderId(), "/queue/messages", ChatNotification.builder().message(message).chat(senderChat).build());
    }

    @MessageMapping("/read")
    public void processReadChat(@Payload ChatReadDto chatReadDto) {
        messageService.markChatAsRead(chatReadDto.getChatId(), chatReadDto.getReceiverId());
    }

}
