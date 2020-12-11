package net.xilla.spigotcore;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

public class SpigotCore extends JavaPlugin {

    private SpigotAPI core;

    @Override
    public void onEnable() {
        this.core = new SpigotAPI(this);
        new SpigotCommands();
    }

    @Override
    public void onDisable() {
        core.onDisable();
    }

}
