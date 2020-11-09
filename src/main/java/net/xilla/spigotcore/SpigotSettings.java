package net.xilla.spigotcore;

import net.xilla.core.library.config.Config;

public class SpigotSettings extends Config {

    public SpigotSettings() {
        super("settings.json");

        setDefault("placeholder-separator", "%");
        setDefault("keep-offline-players-in-cache", false);
    }

}
