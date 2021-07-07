package edu.austral.ingsis.jibberjabberchat.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatReadDto {

    private String chatId;
    private String receiverId;

}
