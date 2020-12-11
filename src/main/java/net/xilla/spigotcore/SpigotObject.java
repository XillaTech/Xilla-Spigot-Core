package net.xilla.spigotcore;

import net.xilla.core.library.XillaLibrary;
import net.xilla.spigotcore.placeholder.PlaceholderAPI;
import net.xilla.spigotcore.placeholder.PlaceholderManager;
import net.xilla.spigotcore.player.PlayerAPI;
import net.xilla.spigotcore.player.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Server;

public interface SpigotObject extends XillaLibrary {

    default SpigotAPI getCore() {
        return SpigotAPI.getInstance();
    }

    default Server getServer() {
        return SpigotAPI.getInstance().getPlugin().getServer();
    }

    default String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    default PlayerManager getPlayerManager() {
        return getCore().getPlayerManager();
    }

    default PlaceholderManager getPlaceholderManager() {
        return getCore().getPlaceholderManager();
    }

}
