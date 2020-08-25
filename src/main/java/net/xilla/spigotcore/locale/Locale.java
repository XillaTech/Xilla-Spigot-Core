package net.xilla.spigotcore.locale;

import net.xilla.spigotcore.settings.Settings;
import org.bukkit.ChatColor;

public class Locale extends Settings {

    private static Locale instance = new Locale();

    public static Locale getInstance() {
        return instance;
    }

    private Locale() {
        super("Locale", "language.json");
        getConfig().loadDefault("prefix", "&8[&3Core&8]");
        getConfig().save();
    }

    public Locale(String plugin) {
        super("Locale", "language-" + plugin + ".json");
        getConfig().loadDefault("prefix", "&8[&3" + plugin + "&8] &f");
        getConfig().save();
    }

    public String getPrefix() {
        return getConfig().getString("prefix");
    }

    public String getMessage(String key, String defaultData) {
        if(getConfig().loadDefault(key, defaultData))
            getConfig().save();
        return getConfig().getString(key);
    }

    public String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

}
