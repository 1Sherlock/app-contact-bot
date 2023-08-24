package uz.cosmos.appcontactbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.cosmos.appcontactbot.entity.TelegramChat;
import uz.cosmos.appcontactbot.entity.TelegramGroup;

import java.util.UUID;

public interface TelegramGroupRepository extends JpaRepository<TelegramGroup, UUID> {
    Boolean existsByChatId(Long chatId);
    TelegramGroup findFirstByOrderBySessionCountDesc();

    TelegramGroup findByChatId(Long chatId);

}
