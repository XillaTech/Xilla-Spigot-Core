package net.xilla.spigotcore;

import net.xilla.spigotcore.settings.Settings;

public class SpigotSettings extends Settings {

    public SpigotSettings() {
        super("Spigot", "settings.json");

        getConfig().loadDefault("placeholder-separator", "%");
        getConfig().loadDefault("keep-offline-players-in-cache", false);
    }

}
