package uz.cosmos.appcontactbot.bot;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.cosmos.appcontactbot.entity.TelegramChat;
import uz.cosmos.appcontactbot.entity.enums.TelegramChatStatus;
import uz.cosmos.appcontactbot.repository.TelegramChatRepository;
import uz.cosmos.appcontactbot.repository.TelegramGroupRepository;
import uz.cosmos.appcontactbot.service.BotService;

@Component
public class ContactBot extends TelegramLongPollingBot {

    @Autowired
    TelegramGroupRepository telegramGroupRepository;

    @Autowired
    TelegramChatRepository telegramChatRepository;

    @Autowired
    BotService botService;

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.username}")
    private String botUsername;

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            TelegramChat chat = telegramChatRepository.findByChatId(update.getMessage().getChat().getId());

            if (update.getMessage().hasText()) {
                String text = update.getMessage().getText();

                if (update.getMessage().isReply() && chat != null && telegramGroupRepository.existsByChatId(chat.getChatId())) {
                    Message repliedMessage = update.getMessage().getReplyToMessage();
                    System.out.println(repliedMessage.getFrom().getId());

                    String repliedText = repliedMessage.getText();
                    String senderChatId = repliedText.substring(8, repliedText.indexOf("ФИО") - 1);

                    TelegramChat byChatId = telegramChatRepository.findByChatId(Long.valueOf(senderChatId));
                    if (byChatId.getStatus() == TelegramChatStatus.SEND_MESSAGE) {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(senderChatId);
                        sendMessage.setText(text);
                        this.execute(sendMessage);
                    }
                } else if (!update.getMessage().isGroupMessage()) {
                    if (text.equals("/start")) {
                        botService.welcomeText(update);
                    } else if (chat != null && chat.getStatus().equals(TelegramChatStatus.SEND_FULL_NAME)) {
                        botService.saveFullName(update, text, chat);
                    } else if (chat != null && chat.getStatus().equals(TelegramChatStatus.SEND_MESSAGE)) {
                        if (text.equals("Bekor qilish")) {
                            botService.cancelChat(update, chat);
                        } else {
                            botService.sendMessageToOperator(update, chat);
                        }
                    }
                }
            } else if (update.getMessage().hasContact()) {
                Contact contact = update.getMessage().getContact();

                if (chat.getStatus().equals(TelegramChatStatus.SEND_PHONE_NUMBER)) {
                    botService.savePhoneNumber(update, contact, chat);
                }

            }
        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();

            try {
                if (data.startsWith("facults#")) {
                    execute(botService.deleteTopMessage(update));
                    botService.sendFacults(update);
                } else if (data.startsWith("goMenu#")) {
                    execute(botService.deleteTopMessage(update));
                    botService.goMenu(update);
                } else if (data.startsWith("contactWithOperator#")) {
                    execute(botService.deleteTopMessage(update));
                    botService.chatWithOperator(update);
                } else if (data.startsWith("contractPrice#")) {
                    execute(botService.deleteTopMessage(update));
                    botService.sendContracts(update);
                } else if (data.startsWith("onlineRegister#")) {
                    execute(botService.deleteTopMessage(update));
                    botService.sendOnline(update);
                }else if (data.startsWith("registerWithDtm#")) {
                    execute(botService.deleteTopMessage(update));
                    botService.registerWithDtm(update);
                }else if (data.startsWith("location#")) {
                    execute(botService.deleteTopMessage(update));
                    botService.location(update);
                }else if (data.startsWith("aboutLicense#")) {
                    execute(botService.deleteTopMessage(update));
                    botService.aboutLicense(update);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
