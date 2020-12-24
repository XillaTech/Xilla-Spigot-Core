package net.xilla.spigotcore.reflection;

import net.xilla.core.library.config.ConfigFile;
import net.xilla.core.reflection.Reflection;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.lang.reflect.Field;

public class SoundReflection extends Reflection<Sound> {

    public SoundReflection() {
        super(Sound.class);
    }

    @Override
    public Sound loadFromSerializedData(ConfigFile file, Object o, Field field, Object o1) {
        return Sound.valueOf(o1.toString());
    }

    @Override
    public Object getSerializedData(ConfigFile file, Object o, Field field, Sound sound) {
        return sound.name();
    }

}
