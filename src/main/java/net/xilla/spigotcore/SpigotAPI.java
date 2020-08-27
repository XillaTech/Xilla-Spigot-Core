package net.xilla.spigotcore;

import com.tobiassteely.tobiasapi.TobiasAPI;
import com.tobiassteely.tobiasapi.api.manager.CoreManager;
import com.tobiassteely.tobiasapi.api.worker.WorkerManager;
import com.tobiassteely.tobiasapi.command.CommandManager;
import com.tobiassteely.tobiasapi.config.ConfigManager;
import net.xilla.spigotcore.locale.Locale;
import net.xilla.spigotcore.placeholder.Placeholder;
import net.xilla.spigotcore.placeholder.PlaceholderAPI;
import net.xilla.spigotcore.player.PlayerAPI;
import net.xilla.spigotcore.player.SpigotPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class SpigotAPI {

    public static SpigotCore getCore() {
        return SpigotCore.getInstance();
    }

    public static SpigotObject getSpigotObject() {
        return new SpigotObject();
    }

    public static TobiasAPI getAPI() {
        return SpigotCore.getInstance().getApi();
    }

    public static Server getServer() {
        return SpigotCore.getInstance().getServer();
    }

    public static Locale getLocale() {
        return Locale.getInstance();
    }

    public static SpigotSettings getSettings() {
        return SpigotCore.getInstance().getSettings();
    }

    public static String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static PlayerAPI getPlayerAPI() {
        return SpigotCore.getInstance().getPlayerAPI();
    }

    public static PlaceholderAPI getPlaceholderAPI() {
        return SpigotCore.getInstance().getPlaceholderAPI();
    }

    public static CommandManager getCommandManager() {
        return getAPI().getCommandManager();
    }

    public static ConfigManager getConfigManager() {
        return getAPI().getConfigManager();
    }

    public static WorkerManager getWorkerManager() {
        return getAPI().getWorkerManager();
    }

    public static CoreManager getCoreManager() {
        return getAPI().getManager();
    }

}
