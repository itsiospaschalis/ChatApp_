package app.chat.springapi.models.dtos.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    private String id;
    private String content;

    private Long chatId;
    private Instant createdAt;
    private Long createdByUserId;

}
