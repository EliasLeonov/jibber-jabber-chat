package edu.austral.ingsis.jibberjabberchat.controller;

import edu.austral.ingsis.jibberjabberchat.domain.Message;
import edu.austral.ingsis.jibberjabberchat.domain.Room;
import edu.austral.ingsis.jibberjabberchat.service.MessageService;
import edu.austral.ingsis.jibberjabberchat.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
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
    public List<Room> getAllChats(@PathVariable(name = "userId") String userId){
        final List<Room> chats = this.roomService.getAllRooms(userId).stream().map(r -> {
            final long unreadCount = messageService.getUnReadCount(r.getChatId(), userId);
            r.setUnreadCount(unreadCount);
            return r;
        }).collect(Collectors.toList());
        return chats;
    }

}
