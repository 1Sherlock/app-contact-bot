package uz.cosmos.appcontactbot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.cosmos.appcontactbot.entity.template.AbstractEntity;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "telegram_groups")
public class TelegramGroup extends AbstractEntity {
    private String name;
    private Long chatId;
    private Integer sessionCount;
}
