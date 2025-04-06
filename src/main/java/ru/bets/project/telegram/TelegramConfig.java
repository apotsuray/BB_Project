package ru.bets.project.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class TelegramConfig {

    static private String BOT_TOKEN;
    static private String BOT_USERNAME;
    static private String chatId;
    @Autowired
    public TelegramConfig(Environment env){
        BOT_TOKEN =  env.getRequiredProperty("BOT_TOKEN");
        BOT_USERNAME = env.getRequiredProperty("BOT_USERNAME");
        chatId = env.getRequiredProperty("CHAT_ID");
    }
    public String  getBotToken() {
        return BOT_TOKEN;
         }
    public String getBotUsername() {
        return BOT_USERNAME;
    }
    public String getChatId() {
        return chatId;
    }
}
