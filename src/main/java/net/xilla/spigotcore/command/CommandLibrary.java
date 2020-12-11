package net.xilla.spigotcore.command;

import net.xilla.spigotcore.SpigotObject;
import org.bukkit.ChatColor;

public class CommandLibrary implements SpigotObject {

    public void sendMessage(CommandData commandData, String message) {
        commandData.getPlayer().sendRawMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

}
