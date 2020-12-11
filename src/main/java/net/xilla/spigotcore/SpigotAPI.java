package net.xilla.spigotcore;

import lombok.Getter;
import net.xilla.core.library.config.ConfigManager;
import net.xilla.core.library.config.extension.ConfigExtension;
import net.xilla.core.library.config.extension.ExtensionManager;
import net.xilla.core.log.Logger;
import net.xilla.spigotcore.locale.Locale;
import net.xilla.spigotcore.reflection.SpigotReflection;
import net.xilla.spigotcore.placeholder.ClipPlaceholderManager;
import net.xilla.spigotcore.placeholder.PlaceholderManager;
import net.xilla.spigotcore.player.PlayerAPI;
import net.xilla.spigotcore.player.PlayerManager;
import net.xilla.spigotcore.util.YamlFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class SpigotAPI {

    private static SpigotAPI instance;

    public static SpigotAPI getInstance() {
        return instance;
    }

    private PlaceholderManager placeholderManager;
    private ClipPlaceholderManager clipPlaceholderManager;
    private PlayerAPI playerAPI;
    private PlayerManager playerManager;
    private SpigotReflection reflection;

    @Getter
    private JavaPlugin plugin;

    public SpigotAPI(@NotNull JavaPlugin plugin) {
        instance = this;

        this.reflection = new SpigotReflection();

        new ConfigManager();

        this.plugin = plugin;
        load(plugin);
    }

    public void load(Plugin plugin) {
        try {
            ConfigManager.getInstance().setBaseFolder(plugin.getDataFolder().getAbsolutePath() + "/");

            YamlFile file = new YamlFile();
            ExtensionManager.getInstance().put(new ConfigExtension("yml", file, "yml"));
            ExtensionManager.getInstance().put(new ConfigExtension("yaml", file, "yml"));

            this.placeholderManager = new PlaceholderManager();
            this.playerAPI = new PlayerAPI();
            this.playerManager = new PlayerManager();

            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                this.clipPlaceholderManager = new ClipPlaceholderManager(this);
                this.clipPlaceholderManager.register();
            }

        } catch (Exception e) {
            Logger.log(e, this.getClass());
        }
    }

    public void onDisable() {
        try {
            this.placeholderManager = null;

            playerManager.save();
            this.playerManager = null;

            this.playerAPI = null;
        } catch (Exception e) {
            Logger.log(e, this.getClass());
        }

        instance = null;
    }


    public static SpigotAPI getCore() {
        return SpigotAPI.getInstance();
    }

    public static SpigotObject getSpigotObject() {
        return new SpigotObject() {};
    }

    public static Locale getLocale() {
        return Locale.getInstance();
    }

    public static String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static PlayerManager getPlayerManager() {
        return getCore().playerManager;
    }

    public static PlaceholderManager getPlaceholderManager() {
        return getCore().placeholderManager;
    }

}
