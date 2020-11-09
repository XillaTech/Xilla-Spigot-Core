package net.xilla.spigotcore.locale;

import net.xilla.core.library.config.Config;
import net.xilla.spigotcore.SpigotAPI;
import org.bukkit.ChatColor;

public class Locale extends Config {

    private static Locale instance = new Locale();

    public static Locale getInstance() {
        return instance;
    }

    private Locale() {
        super( SpigotAPI.getInstance().getPlugin().getDataFolder() + "/language.json");
        setDefault("prefix", "&8[&3Core&8]");
        save();
    }

    public Locale(String plugin) {
        super("language-" + plugin + ".json");
        setDefault("prefix", "&8[&3" + plugin + "&8] &f");
        save();
    }

    public String getPrefix() {
        return getString("prefix");
    }

    public String getMessage(String key, String defaultData) {
        if(setDefault(key, defaultData))
            save();
        return getString(key);
    }

    public String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

}
