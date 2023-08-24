package uz.cosmos.appcontactbot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.cosmos.appcontactbot.entity.enums.TelegramChatStatus;
import uz.cosmos.appcontactbot.entity.template.AbstractEntity;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "telegram_chats")
public class TelegramChat extends AbstractEntity {
    private Long chatId;

    private TelegramChatStatus status;

    private String fullName;

    private String phoneNumber;

    private Long operatorChatId;

    public TelegramChat(Long chatId, TelegramChatStatus status) {
        this.chatId = chatId;
        this.status = status;
    }
}
