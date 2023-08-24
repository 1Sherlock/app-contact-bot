package uz.cosmos.appcontactbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.cosmos.appcontactbot.entity.TelegramChat;

import java.util.UUID;

public interface TelegramChatRepository extends JpaRepository<TelegramChat, UUID> {
    TelegramChat findByChatId(Long chatId);
}
