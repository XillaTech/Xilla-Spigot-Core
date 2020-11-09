package net.xilla.spigotcore;

import lombok.Getter;
import net.xilla.core.library.config.ConfigManager;
import net.xilla.core.log.Log;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.spigotcore.locale.Locale;
import net.xilla.spigotcore.placeholder.ClipPlaceholderManager;
import net.xilla.spigotcore.placeholder.PlaceholderAPI;
import net.xilla.spigotcore.placeholder.PlaceholderManager;
import net.xilla.spigotcore.player.PlayerAPI;
import net.xilla.spigotcore.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class SpigotAPI {

    private static SpigotAPI instance;

    public static SpigotAPI getInstance() {
        return instance;
    }

    private SpigotSettings settings;
    private PlaceholderManager placeholderManager;
    private ClipPlaceholderManager clipPlaceholderManager;
    private PlaceholderAPI placeholderAPI;
    private PlayerAPI playerAPI;
    private PlayerManager playerManager;

    @Getter
    private Plugin plugin;

    public SpigotAPI(@NotNull Plugin plugin) {
        instance = this;
        Logger.setLogger(new Log() {
            @Override
            public void log(LogLevel logLevel, String s, Class aClass) {
                if(logLevel == LogLevel.DEBUG) {
                    Bukkit.getLogger().info("[DEBUG] (" + aClass.getName() + "): " + s);
                } else if(logLevel == LogLevel.INFO) {
                    Bukkit.getLogger().info("(" + aClass.getName() + "): " + s);
                } else if(logLevel == LogLevel.WARN) {
                    Bukkit.getLogger().warning("(" + aClass.getName() + "): " + s);
                } else if(logLevel == LogLevel.ERROR) {
                    Bukkit.getLogger().severe("(" + aClass.getName() + "): " + s);
                } else if(logLevel == LogLevel.FATAL) {
                    Bukkit.getLogger().severe("(" + aClass.getName() + "): " + s);
                }
            }

            @Override
            public void log(LogLevel logLevel, Throwable message, Class aClass) {
                log(logLevel, "Error caught - " + message.getMessage(), aClass);
                for(int i = 0; i < message.getStackTrace().length; i++) {
                    log(logLevel, "[ERR] " + message.getStackTrace()[i].toString(), aClass);
                }
            }

            @Override
            public void log(Throwable throwable, Class aClass) {
                log(LogLevel.ERROR, throwable, aClass);
            }
        });
        this.plugin = plugin;
        load(plugin);
    }

    public void load(Plugin plugin) {
        try {
            this.settings = new SpigotSettings();
            this.placeholderManager = new PlaceholderManager();
            this.playerAPI = new PlayerAPI();
            this.playerManager = new PlayerManager();
            this.placeholderAPI = new PlaceholderAPI();

            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                new ClipPlaceholderManager(this).register();
            }

            ConfigManager.getInstance().setBaseFolder(plugin.getDataFolder().getAbsolutePath());
        } catch (Exception e) {
            Logger.log(e, this.getClass());
        }
    }

    public void onDisable() {
        try {
            settings.save();
            this.settings = null;

            this.placeholderManager = null;

            playerManager.save();
            this.playerManager = null;

            if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
//                this.clipPlaceholderManager.unregister();
                this.clipPlaceholderManager = null;
            }

            this.placeholderAPI = null;
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
        return new SpigotObject();
    }

    public static Locale getLocale() {
        return Locale.getInstance();
    }

    public static SpigotSettings getSettings() {
        return SpigotAPI.getInstance().getSettings();
    }

    public static String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static PlayerAPI getPlayerAPI() {
        return SpigotAPI.getInstance().getPlayerAPI();
    }

    public static PlaceholderAPI getPlaceholderAPI() {
        return SpigotAPI.getInstance().getPlaceholderAPI();
    }

    public static PlayerManager getPlayerManager() {
        return getCore().getPlayerManager();
    }

    public static PlaceholderManager getPlaceholderManager() {
        return getCore().getPlaceholderManager();
    }

}
