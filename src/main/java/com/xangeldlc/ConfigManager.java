package com.xangeldlc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Arrays;

public class ConfigManager {

    private final JavaPlugin plugin;
    private FileConfiguration config;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
    }

    public List<String> getAchievementMessages(String type) {
        for (String key : config.getConfigurationSection("achievements").getKeys(false)) {
            if (config.getString("achievements." + key + ".type").equals(type)) {
                return config.getStringList("achievements." + key + ".message");
            }
        }
        return Arrays.asList(config.getString("default_message"));
    }

    public List<String> getHoverMessages(String type) {
        for (String key : config.getConfigurationSection("achievements").getKeys(false)) {
            if (config.getString("achievements." + key + ".type").equals(type)) {
                return config.getStringList("achievements." + key + ".hover");
            }
        }
        return null;
    }

    public String getTitle(String type) {
        for (String key : config.getConfigurationSection("achievements").getKeys(false)) {
            if (config.getString("achievements." + key + ".type").equals(type)) {
                return config.getString("achievements." + key + ".title", "");
            }
        }
        return "";
    }

    public String getSubtitle(String type) {
        for (String key : config.getConfigurationSection("achievements").getKeys(false)) {
            if (config.getString("achievements." + key + ".type").equals(type)) {
                return config.getString("achievements." + key + ".subtitle", "");
            }
        }
        return "";
    }

    // New method to obtain the commands
    public List<String> getCommands(String type) {
        for (String key : config.getConfigurationSection("achievements").getKeys(false)) {
            if (config.getString("achievements." + key + ".type").equals(type)) {
                return config.getStringList("achievements." + key + ".commands");
            }
        }
        return null;
    }   
}
