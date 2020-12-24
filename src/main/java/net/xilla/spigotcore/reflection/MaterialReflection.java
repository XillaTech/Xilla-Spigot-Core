package net.xilla.spigotcore.reflection;

import net.xilla.core.library.config.ConfigFile;
import net.xilla.core.reflection.Reflection;
import org.bukkit.Material;

import java.lang.reflect.Field;

public class MaterialReflection extends Reflection<Material> {

    public MaterialReflection() {
        super(Material.class);
    }

    @Override
    public Material loadFromSerializedData(ConfigFile file, Object o, Field field, Object o1) {
        return Material.getMaterial(o1.toString());
    }

    @Override
    public Object getSerializedData(ConfigFile file, Object o, Field field, Material material) {
        return material.name();
    }

}
