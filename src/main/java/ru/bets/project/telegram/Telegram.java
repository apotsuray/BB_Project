package ru.bets.project.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bets.project.fonbet.ParserFonbet;
import ru.bets.project.secretbk.ParserSecretBK;

import java.util.ArrayList;
import java.util.List;

@Component
public class Telegram extends TelegramLongPollingBot {
    private ParserFonbet parserFonbet;
    private ParserSecretBK parserSecretBK;
    private final TelegramConfig telegramConfig;
    private String flag;
    @Autowired
    public Telegram(TelegramConfig telegramConfig) {
        this.telegramConfig = telegramConfig;
    }
    public void sendMessageToChat(String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramConfig.getChatId());
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
        String text;
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            SendMessage response = new SendMessage();
            response.setChatId(chatId);
            try {
                switch (messageText) {
                    case "/start":
                        sendStartMessage(chatId);
                        sendMenu(chatId);
                        flag = "zero";
                        break;
                    case "/help":
                        sendHelpMessage(chatId);
                        break;
                    case "Отследить матч - теннис":
                        text = "Введите полное имя игрока (или часть) на английском. Когда будет лайв, бот предупредит об этом.";
                        flag = "Kassa";
                        response.setText(text);
                        execute(response);
                        break;
                    case "Отследить матч - наст. теннис":
                        text = "Введите полное имя игрока (или часть) на английском. Когда будет лайв, бот предупредит об этом.";
                        flag = "Table";
                        response.setText(text);
                        execute(response);
                        break;
                    case "Отследить карточки":
                        text = "Зайдайте матч и параметры матча.";
                        flag = "Card";
                        response.setText(text);
                        execute(response);
                        break;
                    default:
                        if(flag.equals("zero")) {
                            response.setText("Не распознал команду");
                            execute(response); // Отправляем сообщение
                        }else {
                            switch (flag) {
                                case "Kassa":
                                    parserFonbet.getTrackedMatches().add(messageText);
                                    parserSecretBK.getTrackedMatches().add(messageText);
                                    text = "Отслеживаю матч с участием " + messageText;
                                    flag = "zero";
                                    response.setText(text);
                                    execute(response);
                                    break;
                                case "Table":
                                    //работа с настольным теннисом
                                    text = "Отслеживаю матч с участием " + messageText;
                                    flag = "zero";
                                    response.setText(text);
                                    execute(response);
                                    break;
                                case "Card":
                                    //работа с карточками
                                    text = "карточки ";
                                    flag = "zero";
                                    response.setText(text);
                                    execute(response);
                                    break;
                            }
                        }
                        break;
                }
            } catch (TelegramApiException e) {
                e.printStackTrace(); // Обработка ошибок
            }
        }
        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            String chatId = update.getCallbackQuery().getMessage().getChatId().toString();

            SendMessage message = new SendMessage();
            message.setChatId(chatId);

            switch (callbackData) {
                case "button1":
                    message.setText("");
                    break;
                case "button2":
                    message.setText("");
                    break;
                default:
                    message.setText("Неизвестная команда.");
            }

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
    private void sendMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите действие:");

        // Создание клавиатуры
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true); // Адаптировать размер клавиатуры
        keyboardMarkup.setOneTimeKeyboard(false); // Клавиатура не исчезает после использования

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        // Первая строка кнопок
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Отследить матч - теннис"));
        row1.add(new KeyboardButton("Отследить матч - наст. теннис"));

        // Вторая строка кнопок
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("Отследить карточки"));

        keyboardRows.add(row1);
        keyboardRows.add(row2);

        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendStartMessage(long chatId) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Бот работает.\n\n /help для получения справки.");
        execute(message);
    }

    private void sendHelpMessage(long chatId) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Использовать имена на английском языке. Вписывать можно только часть имени-фамилии.");
        execute(message);
    }
    @Override
    public String getBotToken() {
        return telegramConfig.getBotToken();
    }
    @Override
    public String getBotUsername() {
        return telegramConfig.getBotUsername();
    }
}