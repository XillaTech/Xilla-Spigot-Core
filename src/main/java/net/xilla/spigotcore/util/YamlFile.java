package net.xilla.spigotcore.util;

import lombok.Getter;
import lombok.Setter;
import net.xilla.core.library.config.ConfigFile;
import net.xilla.core.library.config.ConfigSection;
import net.xilla.core.library.json.XillaJson;
import net.xilla.spigotcore.SpigotAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlFile implements ConfigFile {

    @Getter
    @Setter
    private String file = null;

    @Setter
    private YamlConfiguration yaml;

    @Override
    public void save() {
        try {
            yaml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reload() {
        try {
            File f = new File(file);
            if(!f.exists()) {

                // try to save resource, if it does not exist then just create a new file
                try {
                    SpigotAPI.getCore().getPlugin().saveResource(file, false);
                } catch (Exception ex) {
                    f.createNewFile();
                }
            }
            yaml.load(f);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        for(String key : yaml.getKeys(true)) {
            yaml.set(key, null);
        }
    }

    @Override
    public <T> T get(String s) {
        return (T)yaml.get(s);
    }

    @Override
    public void set(String s, Object o) {
        yaml.set(s, o);
    }

    @Override
    public ConfigFile create(String s) {
        YamlFile yamlFile = new YamlFile();
        yamlFile.setFile(s);

        YamlConfiguration y = new YamlConfiguration();
        try {

            File f = new File(s);
            if(!f.exists()) {
                // try to save resource, if it does not exist then just create a new file
                try {
                    SpigotAPI.getCore().getPlugin().saveResource(s, false);
                } catch (Exception ex) {
                    f.createNewFile();
                }
            }

            y.load(s);
            yamlFile.setYaml(y);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return yamlFile;
    }

    @Override
    public XillaJson getIndex() {
        XillaJson json = new XillaJson();

        for(String key : yaml.getKeys(false)) {
            json.put(key, yaml.get(key));
        }

        return json;
    }

    @Override
    public boolean contains(String s) {
        return yaml.contains(s);
    }

    @Override
    public ConfigSection getSection(String s) {
        XillaJson json = new XillaJson();

        ConfigurationSection section = yaml.getConfigurationSection(s);
        for(String key : section.getKeys(false)) {
            json.put(key, section.get(key));
        }

        return new ConfigSection(s, json);
    }

    @Override
    public String getExtension() {
        return "Yaml";
    }
}