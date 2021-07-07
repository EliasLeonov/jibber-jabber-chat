package edu.austral.ingsis.jibberjabberchat.factory;

import edu.austral.ingsis.jibberjabberchat.domain.Message;
import edu.austral.ingsis.jibberjabberchat.domain.MessageStatus;
import edu.austral.ingsis.jibberjabberchat.dto.NewMessageDto;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessageFactory {
    public static Message save(NewMessageDto dto, String chatId, MessageStatus status){
        return Message.builder()
                .chatId(chatId)
                .senderId(dto.getSenderId())
                .receiverId(dto.getReceiverId())
                .message(dto.getMessage())
                .timestamp(new Date())
                .status(status)
                .build();
    }
}
