package net.xilla.spigotcore.reflection;

import net.xilla.core.reflection.Reflection;
import org.bukkit.Location;

import java.lang.reflect.Field;
import java.util.Map;

public class LocationReflection extends Reflection<Location> {

    public LocationReflection() {
        super(Location.class);
    }

    @Override
    public Location loadFromSerializedData(Object o, Field field, Object o1) {
        return Location.deserialize((Map<String, Object>) o1);
    }

    @Override
    public Object getSerializedData(Object o, Field field, Location location) {
        return location.serialize();
    }

}
