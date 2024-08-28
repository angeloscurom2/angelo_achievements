package com.xangeldlc;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEventSource;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class PaperColorUtil {

    public static Component translateColorCodes(String message) {
        if (message.contains("<")) {
            // If the message contains "<", I treat it as a mini-message.
            return MiniMessage.miniMessage().deserialize(message);
        } else if (message.contains("&")) {
            // If the message contains "&", I treat it as a traditional color-coded message
            return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
        } else if (message.contains("#")) {
            // If the message contains "#", I assume it is an RGB code in Legacy format
            return LegacyComponentSerializer.legacySection().deserialize(message);
        } else {
            // If the format is not recognized, we return the message unformatted
            return Component.text(message);
        }
    }

    public static Component mm(String miniMessageString) {
        return MiniMessage.miniMessage().deserialize(miniMessageString);
    }

    public static @Nullable HoverEventSource<?> createHoverComponent(List<String> hoverMessages) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createHoverComponent'");
    }
}
