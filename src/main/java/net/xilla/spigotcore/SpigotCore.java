package net.xilla.spigotcore;

import com.tobiassteely.tobiasapi.TobiasAPI;
import com.tobiassteely.tobiasapi.TobiasBuilder;
import net.xilla.spigotcore.gui.InventoryManager;
import net.xilla.spigotcore.placeholder.ClipPlaceholderManager;
import net.xilla.spigotcore.placeholder.PlaceholderAPI;
import net.xilla.spigotcore.placeholder.PlaceholderManager;
import net.xilla.spigotcore.player.PlayerAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotCore extends JavaPlugin {

    private static SpigotCore instance;

    public static SpigotCore getInstance() {
        return instance;
    }

    private TobiasAPI api;
    private SpigotSettings settings;
    private PlaceholderManager placeholderManager;
    private ClipPlaceholderManager clipPlaceholderManager;
    private PlaceholderAPI placeholderAPI;
    private PlayerAPI playerAPI;

    @Override
    public void onEnable() {
        instance = this;

        TobiasBuilder builder = new TobiasBuilder();
        builder.loadConfigManager(getDataFolder().toPath().toString());
        this.api = builder.build(false);
        this.settings = new SpigotSettings();
        this.placeholderManager = new PlaceholderManager();
        this.placeholderAPI = new PlaceholderAPI();
        this.playerAPI = new PlayerAPI();

        SpigotAPI.getServer().getPluginManager().registerEvents(new InventoryManager(), this);

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new ClipPlaceholderManager(this).register();
        }
    }

    @Override
    public void onDisable() {
        settings.getConfig().save();
        this.settings = null;

        placeholderManager.save();
        this.placeholderManager = null;

        this.clipPlaceholderManager.unregister();
        this.clipPlaceholderManager = null;

        this.placeholderAPI = null;
        this.playerAPI = null;

        instance = null;
        this.api = null;
    }

    public TobiasAPI getApi() {
        return api;
    }

    public SpigotSettings getSettings() {
        return settings;
    }

    public PlaceholderManager getPlaceholderManager() {
        return placeholderManager;
    }

    public ClipPlaceholderManager getClipPlaceholderManager() {
        return clipPlaceholderManager;
    }

    public PlaceholderAPI getPlaceholderAPI() {
        return placeholderAPI;
    }

    public PlayerAPI getPlayerAPI() {
        return playerAPI;
    }
}
