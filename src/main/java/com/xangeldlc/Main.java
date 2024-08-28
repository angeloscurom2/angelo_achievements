package com.xangeldlc;

import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
    

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        getLogger().info("Angelo Achievements se ha activado!");

        configManager = new ConfigManager(this);
        getServer().getPluginManager().registerEvents(new AchievementListener(configManager), this);

        this.getCommand("reloadangeloachievements").setExecutor(new ReloadCommand(this));        
    }

    @Override
    public void onDisable() {
        getLogger().info("Angelo Achievements se ha desactivado!");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

}
