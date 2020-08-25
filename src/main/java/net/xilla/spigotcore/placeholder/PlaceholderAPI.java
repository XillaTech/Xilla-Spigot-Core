package net.xilla.spigotcore.placeholder;

import net.xilla.spigotcore.SpigotAPI;
import org.bukkit.entity.Player;

public class PlaceholderAPI extends SpigotAPI {

    private static PlaceholderAPI instance;

    public static PlaceholderAPI getInstance() {
        return instance;
    }

    public PlaceholderAPI() {
        instance = this;
    }

    public static String injectPlaceholders(String string, Player player) {
        if(player != null) {
            return getCore().getPlaceholderManager().injectPlaceholders(string.replace("%", ""), player);
        } else {
            return injectPlaceholders(string);
        }
    }

    public static String injectPlaceholders(String string) {
        return getCore().getPlaceholderManager().injectPlaceholders(string.replace("%", ""), null);
    }

    public static void addPlaceholder(Placeholder placeholder) {

    }

}
