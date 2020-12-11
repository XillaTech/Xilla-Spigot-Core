package net.xilla.spigotcore.util;

import lombok.Getter;
import net.xilla.core.library.config.Config;
import net.xilla.core.library.config.ConfigManager;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.ManagerObject;

public class SpigotSettings extends ManagerObject {

    @Getter
    private Config config;

    public SpigotSettings(String file) {
        this.config = new Config(file);
        ConfigManager.getInstance().put(config);
        setExtension(config.getConfigFile().getExtension());
    }

    public void startup() {
        load();
        save();
    }

    public void save() {
        XillaJson json = getSerializedData();

        for(Object key : json.getJson().keySet()) {
            Object value = json.getJson().get(key);

            if(value != null) {
                this.config.set(key.toString(), value);
            }
        }

        this.config.save();
    }

    public void load() {
        XillaJson json = config.getJson();

        loadSerializedData(config.getJson());
    }

}
