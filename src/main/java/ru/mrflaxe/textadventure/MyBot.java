package ru.mrflaxe.textadventure;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetUpdates;

import ru.mrflaxe.textadventure.achievment.AchievmentManager;
import ru.mrflaxe.textadventure.configuration.Configuration;
import ru.mrflaxe.textadventure.database.DatabaseManager;
import ru.mrflaxe.textadventure.update.UpdateProvider;
import ru.mrflaxe.textadventure.user.UserProvider;

public class MyBot {
    
    private final TelegramBot myBot;
    private final Configuration config;
    private final Configuration messages;
    private final DatabaseManager databaseManager;
    private final UserProvider userProvider;
    private final AchievmentManager achievmentManager;
    
    public MyBot(Configuration messages, Configuration config, Configuration achievments, DatabaseManager databaseManager) {
        String token = config.getString("bot-token");
        
        this.myBot = new TelegramBot(token);
        this.config = config;
        this.messages = messages;
        this.databaseManager = databaseManager;
        this.userProvider = new UserProvider(databaseManager);
        this.achievmentManager = new AchievmentManager(achievments, databaseManager);
    }
    
    public void launch() {
        registerUpdateListener();
    }
    
    private void registerUpdateListener() {
        UpdateProvider updateProvider = new UpdateProvider(myBot, config, messages, databaseManager, achievmentManager, userProvider);
        myBot.setUpdatesListener(updateProvider, new GetUpdates());
    }
}
