package ru.bets.project.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotInitializer implements CommandLineRunner {

    private final TelegramLongPollingBot telegramBot;

    @Autowired
    public BotInitializer(TelegramLongPollingBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBot);
            System.out.println("Telegram бот успешно запущен!");
        } catch (TelegramApiException e) {
            System.err.println("Ошибка при запуске Telegram бота: " + e.getMessage());
            e.printStackTrace();
        }
    }
}