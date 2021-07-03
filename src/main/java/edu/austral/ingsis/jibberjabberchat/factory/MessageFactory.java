package edu.austral.ingsis.jibberjabberchat.factory;

import edu.austral.ingsis.jibberjabberchat.domain.Message;
import edu.austral.ingsis.jibberjabberchat.domain.MessageStatus;
import edu.austral.ingsis.jibberjabberchat.dto.NewMessageDto;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;

@Component
public class MessageFactory {
    public static Message save(NewMessageDto dto, String chatId, MessageStatus status){
        return Message.builder()
                .chatId(chatId)
                .senderId(dto.getSenderId())
                .receiverId(dto.getReceiverId())
                .message(dto.getMessage())
                .timestamp(Date.valueOf(LocalDateTime.now().toLocalDate()))
                .status(status)
                .build();
    }
}
