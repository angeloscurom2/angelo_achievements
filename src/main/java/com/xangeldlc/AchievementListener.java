package com.xangeldlc;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.advancement.Advancement;

import java.util.List;

public class AchievementListener implements Listener {

    private final ConfigManager configManager;

    public AchievementListener(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) {
        Player player = event.getPlayer();
        Advancement advancement = event.getAdvancement();
        String type = advancement.getKey().toString();

        // Ignore if it is a recipe
        if (type.startsWith("minecraft:recipes")) {
            return; 
        }

        // Handling normal achievements
        List<String> customMessages = configManager.getAchievementMessages(type);
        String achievementName = PlainTextComponentSerializer.plainText().serialize(advancement.displayName());

        for (String message : customMessages) {
            // Use PlaceholderAPI to replace placeholders
            String formattedMessage = message
                    .replace("%player%", player.getName())
                    .replace("%achievements%", achievementName);

            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                formattedMessage = PlaceholderAPI.setPlaceholders(player, formattedMessage);
            }

            Component component = PaperColorUtil.translateColorCodes(formattedMessage);

            // Hover handling if it exists
            List<String> hoverMessages = configManager.getHoverMessages(type);
            if (hoverMessages != null && !hoverMessages.isEmpty()) {
                String hoverMessage = hoverMessages.stream()
                        .map(hover -> hover
                                .replace("%player%", player.getName())
                                .replace("%achievements%", achievementName))
                        .reduce("", (a, b) -> a + "\n" + b);

                if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                    hoverMessage = PlaceholderAPI.setPlaceholders(player, hoverMessage);
                }

                Component hoverComponent = PaperColorUtil.translateColorCodes(hoverMessage);
                component = component.hoverEvent(HoverEvent.showText(hoverComponent));
            }

            player.sendMessage(component);
        }

        // Management of title and subtitle if they exist
        String title = configManager.getTitle(type);
        String subtitle = configManager.getSubtitle(type);

        if (!title.isEmpty() || !subtitle.isEmpty()) {
            Component titleComponent = PaperColorUtil.translateColorCodes(title);
            Component subtitleComponent = PaperColorUtil.translateColorCodes(subtitle);

            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                titleComponent = PaperColorUtil.translateColorCodes(PlaceholderAPI.setPlaceholders(player, title));
                subtitleComponent = PaperColorUtil.translateColorCodes(PlaceholderAPI.setPlaceholders(player, subtitle));
            }

            player.sendTitlePart(net.kyori.adventure.title.TitlePart.TITLE, titleComponent);
            player.sendTitlePart(net.kyori.adventure.title.TitlePart.SUBTITLE, subtitleComponent);
        }

        // Execution of commands if they exist
        List<String> commands = configManager.getCommands(type);
        if (commands != null && !commands.isEmpty()) {
            for (String command : commands) {
                String formattedCommand = command
                        .replace("%player%", player.getName())
                        .replace("%achievements%", achievementName);

                if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                    formattedCommand = PlaceholderAPI.setPlaceholders(player, formattedCommand);
                }

                if (formattedCommand.startsWith("[BROCAST]")) {
                    String broadcastMessage = formattedCommand.replace("[BROCAST]", "").trim();
                    Component broadcastComponent = PaperColorUtil.translateColorCodes(broadcastMessage);
                    Bukkit.broadcast(broadcastComponent);
                } else {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formattedCommand);
                }
            }
        }
    }
}
