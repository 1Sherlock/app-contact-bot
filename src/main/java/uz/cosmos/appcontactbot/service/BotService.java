package uz.cosmos.appcontactbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.cosmos.appcontactbot.bot.ContactBot;
import uz.cosmos.appcontactbot.entity.TelegramChat;
import uz.cosmos.appcontactbot.entity.TelegramGroup;
import uz.cosmos.appcontactbot.entity.enums.TelegramChatStatus;
import uz.cosmos.appcontactbot.repository.TelegramChatRepository;
import uz.cosmos.appcontactbot.repository.TelegramGroupRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BotService {
    @Autowired
    ButtonService buttonService;

    @Autowired
    ContactBot contactBot;

    @Autowired
    TelegramChatRepository telegramChatRepository;

    @Autowired
    TelegramGroupRepository telegramGroupRepository;

    @Autowired
    PhotoService photoService;

    public void welcomeText(Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));

        TelegramChat chat = telegramChatRepository.findByChatId(update.getMessage().getChatId());

        if (chat != null && !chat.getStatus().equals(TelegramChatStatus.SEND_FULL_NAME) && !chat.getStatus().equals(TelegramChatStatus.SEND_PHONE_NUMBER)) {
            sendMessage.setText("Assalom aleykum.\n" +
                    "-Renessans Ta'lim Universiteti xalqaro darajadagi davlat diplomini bitiruvchilariga taqdim qiladi. \n" +
                    "-Taʼlim dasturi milliy va xalqaro andozalarga mos tarzda tuzilgan boʻlib, talabaning har bir daqiqasini unumli oʻtishini taʼminlaydi. Siz  o’qish davomida IELTS, CEFR, TOEFL sertifikatiga ega bo'lasiz.\n" +
                    "-Universitetimizda 40 ga yaqin yo'nalish mavjud.\n" +
                    "Bizda dual ta'lim tizimida darslar olib boriladi, ya’ni ham o'qib, ham ishlash imkoniyati bor.\n" +
                    "\n" +
                    "Ta'lim shakillari:\n" +
                    "-Kunduzgi\n" +
                    "-Sirtqi\n" +
                    "-Kechgi\n" +
                    "-Ikkinchi mutaxasislik.\n" +
                    "\n" +
                    "-Renessans Ta'lim Universitetida yana bir avzallik mavjud bo'lib. Siz kontraktni 4 ga bo'lib to'lashingiz mumkin. Ya'ni sentyabr oyi oxirgacha 25 % ni to'laysiz. Qolganini esa o'quv yili yakunigacha bo'lib to'lash imkoniyatini taqdim etadi. 3 tamonlama shartnoma tuzib, ta'lim krediti ham olsangiz bo'ladi.\n" +
                    "\n" +
                    "\uD83D\uDCDD DTM bali bilan IMTIHONSIZ qabul qilamiz.\n" +
                    "\n" +
                    "-Agar bu yil DTM ga topshirmagan bo'lsangiz. Online ariza va imtihon topshirib ham talabalarimiz safiga qo'shilishingiz mumkin." +
                    "\n\n" +
                    "\uD83D\uDCF0  Yana ham to'liq ma'lumot uchun, \uD83D\uDC47 pastdagi tugmalardan foydalaning !");
            chat.setStatus(TelegramChatStatus.OTHER);
            telegramChatRepository.save(chat);
            sendMessage.setReplyMarkup(buttonService.menuButton());
        } else if (chat == null || chat.getStatus().equals(TelegramChatStatus.SEND_FULL_NAME)) {
            sendMessage.setText("Assalom aleykum.\n" +
                    "-Renessans Ta'lim Universiteti xalqaro darajadagi davlat diplomini bitiruvchilariga taqdim qiladi. \n" +
                    "-Taʼlim dasturi milliy va xalqaro andozalarga mos tarzda tuzilgan boʻlib, talabaning har bir daqiqasini unumli oʻtishini taʼminlaydi. Siz  o’qish davomida IELTS, CEFR, TOEFL sertifikatiga ega bo'lasiz.\n" +
                    "-Universitetimizda 40 ga yaqin yo'nalish mavjud.\n" +
                    "Bizda dual ta'lim tizimida darslar olib boriladi, ya’ni ham o'qib, ham ishlash imkoniyati bor.\n" +
                    "\n" +
                    "Ta'lim shakillari:\n" +
                    "-Kunduzgi\n" +
                    "-Sirtqi\n" +
                    "-Kechgi\n" +
                    "-Ikkinchi mutaxasislik.\n" +
                    "\n" +
                    "-Renessans Ta'lim Universitetida yana bir avzallik mavjud bo'lib. Siz kontraktni 4 ga bo'lib to'lashingiz mumkin. Ya'ni sentyabr oyi oxirgacha 25 % ni to'laysiz. Qolganini esa o'quv yili yakunigacha bo'lib to'lash imkoniyatini taqdim etadi. 3 tamonlama shartnoma tuzib, ta'lim krediti ham olsangiz bo'ladi.\n" +
                    "\n" +
                    "\uD83D\uDCDD DTM bali bilan IMTIHONSIZ qabul qilamiz.\n" +
                    "\n" +
                    "-Agar bu yil DTM ga topshirmagan bo'lsangiz. Online ariza va imtihon topshirib ham talabalarimiz safiga qo'shilishingiz mumkin." +
                    "\n\n" +
                    "\uD83D\uDCF0 Yana ham to'liq ma'lumot uchun, ism familiyangizni yozib yuboring: ");

            if (chat == null) {
                telegramChatRepository.save(new TelegramChat(update.getMessage().getChatId(), TelegramChatStatus.SEND_FULL_NAME));
            }
        } else if (chat.getStatus().equals(TelegramChatStatus.SEND_PHONE_NUMBER)) {
            sendMessage.setText("Assalom aleykum.\n" +
                    "-Renessans Ta'lim Universiteti xalqaro darajadagi davlat diplomini bitiruvchilariga taqdim qiladi. \n" +
                    "-Taʼlim dasturi milliy va xalqaro andozalarga mos tarzda tuzilgan boʻlib, talabaning har bir daqiqasini unumli oʻtishini taʼminlaydi. Siz  o’qish davomida IELTS, CEFR, TOEFL sertifikatiga ega bo'lasiz.\n" +
                    "-Universitetimizda 40 ga yaqin yo'nalish mavjud.\n" +
                    "Bizda dual ta'lim tizimida darslar olib boriladi, ya’ni ham o'qib, ham ishlash imkoniyati bor.\n" +
                    "\n" +
                    "Ta'lim shakillari:\n" +
                    "-Kunduzgi\n" +
                    "-Sirtqi\n" +
                    "-Kechgi\n" +
                    "-Ikkinchi mutaxasislik.\n" +
                    "\n" +
                    "-Renessans Ta'lim Universitetida yana bir avzallik mavjud bo'lib. Siz kontraktni 4 ga bo'lib to'lashingiz mumkin. Ya'ni sentyabr oyi oxirgacha 25 % ni to'laysiz. Qolganini esa o'quv yili yakunigacha bo'lib to'lash imkoniyatini taqdim etadi. 3 tamonlama shartnoma tuzib, ta'lim krediti ham olsangiz bo'ladi.\n" +
                    "\n" +
                    "\uD83D\uDCDD DTM bali bilan IMTIHONSIZ qabul qilamiz.\n" +
                    "\n" +
                    "-Agar bu yil DTM ga topshirmagan bo'lsangiz. Online ariza va imtihon topshirib ham talabalarimiz safiga qo'shilishingiz mumkin." +
                    "\n\n" +
                    "\uD83D\uDCF0 Yana ham to'liq ma'lumot uchun, telefon raqamingizni yuboring: ");
            sendMessage.setReplyMarkup(buttonService.enterNumber());
        }
        contactBot.execute(sendMessage);
    }

    public void saveFullName(Update update, String text, TelegramChat chat) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText("Iltimos, telefon raqamingizni yuboring:");
        sendMessage.setReplyMarkup(buttonService.enterNumber());

        chat.setStatus(TelegramChatStatus.SEND_PHONE_NUMBER);
        chat.setFullName(text);
        telegramChatRepository.save(chat);

        contactBot.execute(sendMessage);
    }

    public void savePhoneNumber(Update update, Contact contact, TelegramChat chat) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText("Ma'lumotlar muvaffaqqiyatli saqlandi, chat-botdan foydalanishda davom etishingiz mumkin.");

        chat.setPhoneNumber(contact.getPhoneNumber());
        chat.setStatus(TelegramChatStatus.OTHER);
        telegramChatRepository.save(chat);

        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove(true);
        sendMessage.setReplyMarkup(replyKeyboardRemove);
        contactBot.execute(sendMessage);
        sendMessage.setText("Tanlang, sizni nima qiziqtiradi:");
        sendMessage.setReplyMarkup(buttonService.menuButton());
        contactBot.execute(sendMessage);
    }

    public DeleteMessage deleteTopMessage(Update update) {
        DeleteMessage deleteMessage = new DeleteMessage();
        if (update.hasMessage()) {
            deleteMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
            deleteMessage.setMessageId(update.getMessage().getMessageId());
        } else if (update.hasCallbackQuery()) {
            deleteMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
            deleteMessage.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        }
        return deleteMessage;
    }


    public void sendFacults(Update update) throws TelegramApiException, IOException {
        Resource photoResource = photoService.readPhotoFromResources("images/facults_1.jpg");
        Resource photoResource2 = photoService.readPhotoFromResources("images/facults_2.jpg");
        Resource photoResource3 = photoService.readPhotoFromResources("images/facults_3.jpg");
        Resource photoResource4 = photoService.readPhotoFromResources("images/facults_4.jpg");
        Resource photoResource5 = photoService.readPhotoFromResources("images/facults_5.jpg");
        Resource photoResource6 = photoService.readPhotoFromResources("images/facults_6.jpg");
        Resource photoResource7 = photoService.readPhotoFromResources("images/facults_7.jpg");

        SendMediaGroup sendMediaGroup = new SendMediaGroup();
        sendMediaGroup.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        List<InputMedia> inputMediaList = new ArrayList<>();
        InputMedia photo1 = new InputMediaPhoto();
        photo1.setMedia(photoResource.getInputStream(), "facults_1.jpg");
        inputMediaList.add(photo1);
        InputMedia photo2 = new InputMediaPhoto();
        photo2.setMedia(photoResource2.getInputStream(), "facults_2.jpg");
        inputMediaList.add(photo2);
        InputMedia photo3 = new InputMediaPhoto();
        photo3.setMedia(photoResource3.getInputStream(), "facults_3.jpg");
        inputMediaList.add(photo3);
        InputMedia photo4 = new InputMediaPhoto();
        photo4.setMedia(photoResource4.getInputStream(), "facults_4.jpg");
        inputMediaList.add(photo4);
        InputMedia photo5 = new InputMediaPhoto();
        photo5.setMedia(photoResource5.getInputStream(), "facults_5.jpg");
        inputMediaList.add(photo5);
        InputMedia photo6 = new InputMediaPhoto();
        photo6.setMedia(photoResource6.getInputStream(), "facults_6.jpg");
        inputMediaList.add(photo6);
        InputMedia photo7 = new InputMediaPhoto();
        photo7.setMedia(photoResource7.getInputStream(), "facults_7.jpg");
        photo7.setParseMode("html");
        photo7.setCaption("\uD83C\uDF93RENESSANS taʼlim Universitetida yo'nalishlar soni ortdi.\n" +
                "\n" +
                "\uD83D\uDD3BYoʻnalishlarning ko'pligi, sizga imkoniyatlar eshigini yanada kengroq ochib beradi.\n" +
                "\n" +
                "♾Endilikda Universitetda 6ta fakultet tarkibidagi 40ga yaqin yoʻnalishlardan birini tanlashingiz mumkin:\n" +
                "\n" +
                "Pedagogika;\n" +
                "✅Iqtisodiyot va biznes\n" +
                "✅Filologiya va tillarni o‘qitish (O‘zb/Rus/Eng)\n" +
                "✅Axborot texnologiyalari\n" +
                "✅Arxitektura va shaharsozlik\n" +
                "✅Xalqaro iqtisodiy aloqalar\n" +
                "\n" +
                "\uD83D\uDC68\u200D\uD83C\uDFEBTaʼlim dasturi milliy va xalqaro andozalarga mos tarzda tuzilgan boʻlib, talabaning har bir daqiqasini unumli oʻtishini taʼminlaydi.\n" +
                "\n" +
                "\uD83D\uDCABBarchasi Sizning yorqin kelajagingiz uchun !\n" +
                "\n" +
                "\uD83D\uDCE5Hujjatlar https://renessans-edu.uz/uz/cv internet manzil orqali qabul qilinadi\n" +
                "\n" +
                "☎️ Murojaat uchun:\n" +
                "+998555067007 | <a href='http://t.me/Renessans_edu_uz'>Yozish</a>\n" +
                "+998555068008 | <a href='http://t.me/Renessans_edu_uz'>Yozish</a>\n" +
                "\n" +
                "\uD83D\uDCCDToshkent sh, Mirobod tumani, Mirobod ko’chasi 41/4\n" +
                "\n" +
                "⚡️<a href='https://t.me/renessansedu_uz/4'>(Litsenziya №049194, 18.11.2022y.)</a>\n" +
                "\n" +
                "<a href='https://t.me/renessansedu_uz'>Telegram</a> | <a href='https://www.instagram.com/renessans.edu.uz/'>Instagram</a> | <a href='https://www.facebook.com/Renessans-104914062493912/'>Facebook</a> | <a href='https://youtube.com/@renessansuniversity'>Youtube</a>");
        inputMediaList.add(photo7);

        sendMediaGroup.setMedias(inputMediaList);
        contactBot.execute(sendMediaGroup);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText("Tanlang:");
        sendMessage.setReplyMarkup(buttonService.goBack("goMenu#"));
        contactBot.execute(sendMessage);
    }

    public void goMenu(Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText("Tanlang, sizni nima qiziqtiradi:");
        sendMessage.setReplyMarkup(buttonService.menuButton());
        contactBot.execute(sendMessage);
    }

    public void chatWithOperator(Update update) throws TelegramApiException {
        TelegramChat chat = telegramChatRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText("Iltimos, murojaatingizni yozing:");
        chat.setStatus(TelegramChatStatus.SEND_MESSAGE);
        TelegramGroup telegramGroup = telegramGroupRepository.findFirstByOrderBySessionCountDesc();
        chat.setOperatorChatId(telegramGroup.getChatId());
        telegramChatRepository.save(chat);
        telegramGroup.setSessionCount((telegramGroup.getSessionCount() != null ? telegramGroup.getSessionCount() : 0) + 1);
        telegramGroupRepository.save(telegramGroup);
        sendMessage.setReplyMarkup(buttonService.cancelButton());
        contactBot.execute(sendMessage);
    }

    public void cancelChat(Update update, TelegramChat chat) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chat.getChatId()));


        TelegramGroup telegramGroup = telegramGroupRepository.findByChatId(chat.getOperatorChatId());
        telegramGroup.setSessionCount(telegramGroup.getSessionCount() - 1);
        telegramGroupRepository.save(telegramGroup);

        chat.setStatus(TelegramChatStatus.OTHER);
        chat.setOperatorChatId(null);
        telegramChatRepository.save(chat);

        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove(true);
        sendMessage.setText("Bekor qilindi");
        sendMessage.setReplyMarkup(replyKeyboardRemove);
        contactBot.execute(sendMessage);
        sendMessage.setText("Tanlang, sizni nima qiziqtiradi: ");
        sendMessage.setReplyMarkup(buttonService.menuButton());
        contactBot.execute(sendMessage);
    }

    public void sendMessageToOperator(Update update, TelegramChat chat) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chat.getOperatorChatId()));
        sendMessage.setText("ChatID: " + chat.getChatId() + "\n" +
                "ФИО: " + chat.getFullName() + "\n" +
                "Номер телефона: " + chat.getPhoneNumber() + "\n\n" + update.getMessage().getText());
        contactBot.execute(sendMessage);
    }

    public void sendContracts(Update update) throws IOException, TelegramApiException {
        Resource photoResource = photoService.readPhotoFromResources("images/contracts_1.jpg");
        Resource photoResource2 = photoService.readPhotoFromResources("images/contracts_2.jpg");
        Resource photoResource3 = photoService.readPhotoFromResources("images/contracts_3.jpg");
        Resource photoResource4 = photoService.readPhotoFromResources("images/contracts_4.jpg");

        SendMediaGroup sendMediaGroup = new SendMediaGroup();
        sendMediaGroup.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        List<InputMedia> inputMediaList = new ArrayList<>();
        InputMedia photo1 = new InputMediaPhoto();
        photo1.setMedia(photoResource.getInputStream(), "contracts_1.jpg");
        inputMediaList.add(photo1);
        InputMedia photo2 = new InputMediaPhoto();
        photo2.setMedia(photoResource2.getInputStream(), "contracts_2.jpg");
        inputMediaList.add(photo2);
        InputMedia photo3 = new InputMediaPhoto();
        photo3.setMedia(photoResource3.getInputStream(), "contracts_3.jpg");
        inputMediaList.add(photo3);
        InputMedia photo4 = new InputMediaPhoto();
        photo4.setMedia(photoResource4.getInputStream(), "contracts_4.jpg");
        photo4.setParseMode("html");
        photo4.setCaption("\uD83C\uDFAF Renessans Ta'lim Universitetida yana bir avzailik mavjud bo'lib. Siz kantrkatni 4 ga bo'lib to'lashingiz mumkin. Ya'ni sentyabir oyi oxirgacha 25 % ni to'laysiz. Qolganini esa o'quv yili yakunigacha bo'lib to'lsh imkoniyatini taqdim etadi. 3 tamonlama shartnoma tuzib, ta'lim kriditi ham olsangiz bo'ladi.");
        inputMediaList.add(photo4);

        sendMediaGroup.setMedias(inputMediaList);
        contactBot.execute(sendMediaGroup);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText("Tanlang:");
        sendMessage.setReplyMarkup(buttonService.goBack("goMenu#"));
        contactBot.execute(sendMessage);
    }

    public void sendOnline(Update update) throws IOException, TelegramApiException {
        Resource photoResource = photoService.readPhotoFromResources("images/online_1.jpg");
        Resource photoResource2 = photoService.readPhotoFromResources("images/online_2.MOV");

        SendMediaGroup sendMediaGroup = new SendMediaGroup();
        sendMediaGroup.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        List<InputMedia> inputMediaList = new ArrayList<>();
        InputMedia photo1 = new InputMediaPhoto();
        photo1.setMedia(photoResource.getInputStream(), "online_1.jpg");
        inputMediaList.add(photo1);
        InputMedia photo2 = new InputMediaVideo();
        photo2.setMedia(photoResource2.getInputStream(), "online_2.MOV");
        photo2.setParseMode("html");
        photo2.setCaption("\uD83C\uDF93RENESSANS Taʼlim Universiteti—\n" +
                "Xalqaro standartlarga muvofiq yuqori sifatli taʼlim tanlovi!\n" +
                "\n" +
                "✅Online imtihon topshirish video qo’llanmasidan foydalanib, universitetimiz talabasiga aylaning.\n" +
                "\n" +
                "\uD83D\uDCC8Endilikda universitetimizda 40ga yaqin taʼlim  yoʻnalishlaridan birini tanlashingiz mumkin.\n" +
                "\n" +
                "⌛️Taʼlim dasturi milliy va xalqaro andozalarga mos tarzda tuzilgan boʻlib, talabaning har bir daqiqasini unumli oʻtishini taʼminlaydi.\n" +
                "\n" +
                "\uD83D\uDCAFBarcha imkoniyatlar, zamonaviy texnologiyalar  muvaffaqiyatingiz garovidir!\n" +
                "\n" +
                "\uD83D\uDCABBarchasi Sizning yorqin kelajagingiz uchun !\n" +
                "\n" +
                "\uD83D\uDCE5Hujjatlar https://renessans-edu.uz/uz/cv internet manzil orqali qabul qilinadi\n" +
                "\n" +
                "☎️ Murojaat uchun:\n" +
                "+998555067007 | <a href='http://t.me/Renessans_edu_uz'>Yozish</a>\n" +
                "+998555068008 | <a href='http://t.me/Renessans_edu_uz'>Yozish</a>\n" +
                "\n" +
                "\uD83D\uDCCDToshkent sh, Mirobod tumani, Mirobod ko’chasi 41/4\n" +
                "\n" +
                "⚡️<a href='https://t.me/renessansedu_uz/4'>(Litsenziya №049194, 18.11.2022y.)</a>\n" +
                "\n" +
                "<a href='https://t.me/renessansedu_uz'>Telegram</a> | <a href='https://www.instagram.com/renessans.edu.uz/'>Instagram</a> | <a href='https://www.facebook.com/Renessans-104914062493912/'>Facebook</a> | <a href='https://youtube.com/@renessansuniversity'>Youtube</a>");
        inputMediaList.add(photo2);

        sendMediaGroup.setMedias(inputMediaList);
        contactBot.execute(sendMediaGroup);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText("Tanlang:");
        sendMessage.setReplyMarkup(buttonService.goBack("goMenu#"));
        contactBot.execute(sendMessage);
    }

    public void registerWithDtm(Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText("(DTM bali bilan, TALABA bo'l )\n" +
                "Bu yil oqishga topshirgan bolsangi Renessans Ta'lim Universitetiga DTM dan toplagan balingiz bilan imtihonsiz qabul qilinasiz. Buning uchun quyidagi hujjatlarni o'quv binosiga olib kelib berishingiz so'rab qolinadi. \n" +
                "Hujjatlar.... \n" +
                "1.Papsort nusxasi 4 dona.\n" +
                "2. 3/4 Rasm 6 ta. \n" +
                "3.Diplom yoki atesstatni orginali (Ilovasi bilan )\n" +
                "4.DTM varqasi. \n" +
                "\n" +
                "Ish vaqti: 9-00 dan 18-00 gacha. Dushandan shanbagacha.");
        sendMessage.setReplyMarkup(buttonService.goBack("goMenu#"));
        contactBot.execute(sendMessage);
    }

    public void location(Update update) throws IOException, TelegramApiException {
        Resource photoResource = photoService.readPhotoFromResources("images/location.jpg");

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(photoResource.getInputStream(), "location.jpg"));
        sendPhoto.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendPhoto.setParseMode("html");
        sendPhoto.setCaption("\uD83D\uDCA5BIZNING UNIVERSITETIMIZ MANZILI\n" +
                "\n" +
                "<a href='https://goo.gl/maps/qqHseBWP9pj3iKrg7'>\uD83D\uDC49MANZILNI BILISH UCHUN BOSING\uD83D\uDC48</a>\n" +
                "\n" +
                "\uD83C\uDFDBRenessans universitetimizning yaqin atrofida Toshkent va Oybek kabi metro bekatlari bor \n" +
                "\n" +
                "‼️ Bizning universitetimizga olib keladigan transportlar: \n" +
                "\n" +
                "\uD83D\uDE87 Oybek metro bekati (900m uzoqlikda «Anor» kafesining orqa tomonida)\n" +
                "\n" +
                "\uD83D\uDE8C 81 avtobus (Mirobod bozori tomonidan «Anor» kafe bekati)\n" +
                "\n" +
                "\uD83D\uDE96 Taksi (Mirobod avenu yoki «Anor» kafe)\n" +
                "\n" +
                "Renessans Taʼlim Universiteti - xalqaro darajadagi taʼlim maskani!\n" +
                "\n" +
                "HOZIROQ biz bilan bogʻlaning va kerakli hujjatlar ro'yxatini ta’minlasangiz boʻlgani\uD83D\uDE80\n" +
                "\n" +
                "\uD83D\uDCDE 55 506 70 07\n" +
                "\uD83D\uDCDE 55 506 80 08\n" +
                "\n" +
                "\uD83D\uDCE5 Onlayn hujjat topshirish: http://reg.renessans-edu.uz/\n" +
                "\n" +
                "<a href='https://t.me/renessansedu_uz'>Telegram</a> | <a href='https://www.instagram.com/renessans.edu.uz/'>Instagram</a> | <a href='https://www.facebook.com/Renessans-104914062493912/'>Facebook</a> | <a href='https://youtube.com/@renessansuniversity'>Youtube</a>");
        contactBot.execute(sendPhoto);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText("Tanlang:");
        sendMessage.setReplyMarkup(buttonService.goBack("goMenu#"));
        contactBot.execute(sendMessage);
    }

    public void aboutLicense(Update update) throws IOException, TelegramApiException {
        Resource photoResource = photoService.readPhotoFromResources("images/licence.MOV");

        SendVideo sendPhoto = new SendVideo();
        sendPhoto.setVideo(new InputFile(photoResource.getInputStream(), "licence.MOV"));
        sendPhoto.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendPhoto.setParseMode("html");
        sendPhoto.setCaption("\uD83C\uDFDBRenessans Ta’lim Universiteti Vazirlar mahkamasi huzuridagi Ta’lim sifatini nazorat qilish davlat inspeksiyasining litsenziyasiga ega.\n" +
                "\n" +
                "\uD83D\uDC68\u200D\uD83D\uDCBBTaʼlim dasturi milliy va xalqaro andozalarga mos tarzda tuzilgan boʻlib, talabaning har bir daqiqasini unumli oʻtishini taʼminlaydi.\n" +
                "\n" +
                "\uD83D\uDCCAYuqori malakali bakalavriat talabalarini kunduzgi, sirtqi va kechki taʼlim shakllarida, shuningdek, ikkinchi mutaxassislik bo‘yicha o‘zbek, rus va ingliz tillarida tayyorlaydi\n" +
                "\n" +
                "✅\uD83D\uDCDDUniversitet tomonidan berilgan diplom, barcha davlat oliy ta’lim muassalariniki kabi, bir xilda tan olinadi.✔️\n" +
                "\n" +
                "\uD83D\uDCF2Online imtihon topshiring va talabalarimiz safiga qo’shiling.\n" +
                "\n" +
                "\uD83D\uDD14Barchasi Sizning yorqin kelajagingiz uchun !\n" +
                "\n" +
                "\uD83D\uDCE5Hujjatlar https://renessans-edu.uz/uz/cv internet manzil orqali qabul qilinadi\n" +
                "\n" +
                "☎️ Murojaat uchun:\n" +
                "+998555067007 | <a href='http://t.me/Renessans_edu_uz'>Yozish</a>\n" +
                "+998555068008 | <a href='http://t.me/Renessans_edu_uz'>Yozish</a>\n" +
                "\n" +
                "\uD83D\uDCCDToshkent sh, Mirobod tumani, Mirobod ko’chasi 41/4\n" +
                "\n" +
                "⚡️<a href='https://t.me/renessansedu_uz/4'>(Litsenziya №049194, 18.11.2022y.)</a>\n" +
                "\n" +
                "<a href='https://t.me/renessansedu_uz'>Telegram</a> | <a href='https://www.instagram.com/renessans.edu.uz/'>Instagram</a> | <a href='https://www.facebook.com/Renessans-104914062493912/'>Facebook</a> | <a href='https://youtube.com/@renessansuniversity'>Youtube</a>");
        contactBot.execute(sendPhoto);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText("Tanlang:");
        sendMessage.setReplyMarkup(buttonService.goBack("goMenu#"));
        contactBot.execute(sendMessage);
    }
}
