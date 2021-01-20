package net.xilla.spigotcore.reflection.storage;

import net.xilla.core.library.config.ConfigFile;
import net.xilla.core.reflection.storage.StorageReflection;
import net.xilla.spigotcore.util.JsonItemStack;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.Map;

public class LocationReflection extends StorageReflection<Location> {

    public LocationReflection() {
        super(Location.class);
    }

    @Override
    public Location loadFromSerializedData(ConfigFile file, Object base, Field field, Object o) {
        if(file != null && file.getExtension().equalsIgnoreCase("Yaml")) {
            ConfigurationSection section = (ConfigurationSection) o;
            if(section != null) {
                Map<String, Object> map = section.getValues(true);
                return Location.deserialize(map);
            }
        } else {
            return Location.deserialize((Map<String, Object>) o);
        }
        return null;
    }

    @Override
    public Object getSerializedData(ConfigFile file, Object o, Field field, Location location) {
        return location.serialize();
    }

}
