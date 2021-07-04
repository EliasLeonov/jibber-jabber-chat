package edu.austral.ingsis.jibberjabberchat.controller;

import edu.austral.ingsis.jibberjabberchat.domain.Message;
import edu.austral.ingsis.jibberjabberchat.domain.Room;
import edu.austral.ingsis.jibberjabberchat.service.MessageService;
import edu.austral.ingsis.jibberjabberchat.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/chat-api")
public class ChatAPIController {

    private final MessageService messageService;
    private final RoomService roomService;

    @Autowired
    public ChatAPIController(MessageService messageService, RoomService roomService) {
        this.messageService = messageService;
        this.roomService = roomService;
    }



    @GetMapping("/messages/{userId}/{loggedId}")
    public Set<Message> findChatMessages(@PathVariable(name = "userId") String userId, @PathVariable(name = "loggedId") String loggedId){
        return messageService.findMessage(userId, loggedId);
    }

    @GetMapping("/all/{userId}")
    public Set<Room> getAllChats(@PathVariable(name = "userId") String userId){
        return this.roomService.getAllRooms(userId);
    }

}
