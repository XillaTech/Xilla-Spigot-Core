package net.xilla.spigotcore.reflection.storage;

import net.xilla.core.library.config.ConfigFile;
import net.xilla.core.reflection.storage.StorageReflection;
import org.bukkit.Particle;
import org.bukkit.Sound;

import java.lang.reflect.Field;

public class ParticleReflection extends StorageReflection<Particle> {

    public ParticleReflection() {
        super(Particle.class);
    }

    @Override
    public Particle loadFromSerializedData(ConfigFile file, Object o, Field field, Object o1) {
        return Particle.valueOf(o1.toString());
    }

    @Override
    public Object getSerializedData(ConfigFile file, Object o, Field field, Particle particle) {
        return particle.name();
    }

}
