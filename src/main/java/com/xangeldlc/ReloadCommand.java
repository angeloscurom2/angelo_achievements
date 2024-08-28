package com.xangeldlc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {

    private final Main plugin;

    public ReloadCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getLabel().equalsIgnoreCase("reloadangeloachievements")) {
            if (sender.hasPermission("angeloachievements.reload")) {
                plugin.reloadConfig(); // Reload the config.yml file
                plugin.getConfigManager().loadConfig(); // Reload configuration in ConfigManager
                sender.sendMessage("Angelo Achievements settings have been reloaded!");
            } else {
                sender.sendMessage("You do not have permission to run this command.");
            }
            return true;
        }

        return false;
    }
}
