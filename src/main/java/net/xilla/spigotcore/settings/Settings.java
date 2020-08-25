package net.xilla.spigotcore.settings;

import com.tobiassteely.tobiasapi.TobiasAPI;
import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import com.tobiassteely.tobiasapi.command.CommandManager;
import com.tobiassteely.tobiasapi.config.Config;
import com.tobiassteely.tobiasapi.config.ConfigManager;

public class Settings extends ManagerObject {

    private Config config;

    public Settings(String name, String configName) {
        super(name.toLowerCase());
        this.config = getConfigManager().getConfig(configName);
    }

    public Config getConfig() {
        return config;
    }

}
