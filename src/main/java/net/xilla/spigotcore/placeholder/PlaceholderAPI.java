package net.xilla.spigotcore.placeholder;

import net.xilla.spigotcore.SpigotAPI;
import org.bukkit.entity.Player;

import static net.xilla.spigotcore.SpigotAPI.getCore;

public class PlaceholderAPI {

    public static String injectPlaceholders(String string, Player player) {
        if(player != null) {
            return getCore().getPlaceholderManager().injectPlaceholders(string, player);
        } else {
            return injectPlaceholders(string);
        }
    }

    public static String injectPlaceholders(String string) {
        return getCore().getPlaceholderManager().injectPlaceholders(string, null);
    }

    public static void addPlaceholder(Placeholder placeholder) {
        SpigotAPI.getInstance().getPlaceholderManager().put(placeholder);
    }

}
