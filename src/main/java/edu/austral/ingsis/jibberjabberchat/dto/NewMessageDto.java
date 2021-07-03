package edu.austral.ingsis.jibberjabberchat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewMessageDto {
    private String senderId;
    private String receiverId;
    private String message;
}
