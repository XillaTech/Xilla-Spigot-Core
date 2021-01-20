package net.xilla.spigotcore.reflection.storage;

import net.xilla.core.library.config.ConfigFile;
import net.xilla.core.reflection.storage.StorageReflection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.lang.reflect.Field;
import java.util.Map;

public class WorldReflection extends StorageReflection<World> {

    public WorldReflection() {
        super(World.class);
    }

    @Override
    public World loadFromSerializedData(ConfigFile file, Object base, Field field, Object o) {
        return Bukkit.getWorld(o.toString());
    }

    @Override
    public Object getSerializedData(ConfigFile file, Object o, Field field, World world) {
        return world.getName();
    }

}
