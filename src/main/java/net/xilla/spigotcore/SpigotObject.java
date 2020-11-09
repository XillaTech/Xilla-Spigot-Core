package net.xilla.spigotcore;

import net.xilla.core.library.XillaLibrary;
import net.xilla.spigotcore.placeholder.PlaceholderAPI;
import net.xilla.spigotcore.placeholder.PlaceholderManager;
import net.xilla.spigotcore.player.PlayerAPI;
import net.xilla.spigotcore.player.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Server;

public class SpigotObject extends XillaLibrary {

    public SpigotAPI getCore() {
        return SpigotAPI.getInstance();
    }

    public Server getServer() {
        return SpigotAPI.getInstance().getPlugin().getServer();
    }

    public String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public PlayerAPI getPlayerAPI() {
        return getCore().getPlayerAPI();
    }

    public PlayerManager getPlayerManager() {
        return getCore().getPlayerManager();
    }

    public PlaceholderAPI getPlaceholderAPI() {
        return getCore().getPlaceholderAPI();
    }

    public SpigotSettings getSettings() {
        return getCore().getSettings();
    }

    public PlaceholderManager getPlaceholderManager() {
        return getCore().getPlaceholderManager();
    }

}
