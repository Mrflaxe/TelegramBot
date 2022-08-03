package ru.mrflaxe.textadventure;

import java.sql.SQLException;

import ru.mrflaxe.textadventure.configuration.Configuration;
import ru.mrflaxe.textadventure.database.Database;
import ru.mrflaxe.textadventure.database.DatabaseManager;
import ru.mrflaxe.textadventure.database.model.AchievementModel;
import ru.mrflaxe.textadventure.database.model.ProfileModel;
import ru.mrflaxe.textadventure.database.model.SaveModel;

public class Main {
    
    public static void main(String[] args) {
        Configuration messages = new Configuration("messages.yml");
        Configuration config = new Configuration("config.yml");
        Configuration achievments = new Configuration("achievments.yml");
        
        Database database = new Database(config);
        database.establishConnection();
        
        database.createTable(ProfileModel.class);
        database.createTable(SaveModel.class);
        database.createTable(AchievementModel.class);
        
        DatabaseManager databaseManager = null;
        
        try {
            databaseManager = new DatabaseManager(database.getConnection());
        } catch (SQLException e) {
            System.err.println("Failed to create DatabaseManager");
        }
        
        new MyBot(messages, config, achievments, databaseManager).launch();
    }
}
