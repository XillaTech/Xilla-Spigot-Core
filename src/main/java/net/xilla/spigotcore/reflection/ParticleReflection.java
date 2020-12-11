package net.xilla.spigotcore.reflection;

import net.xilla.core.reflection.Reflection;
import org.bukkit.Particle;
import org.bukkit.Sound;

import java.lang.reflect.Field;

public class ParticleReflection extends Reflection<Particle> {

    public ParticleReflection() {
        super(Particle.class);
    }

    @Override
    public Particle loadFromSerializedData(Object o, Field field, Object o1) {
        return Particle.valueOf(o1.toString());
    }

    @Override
    public Object getSerializedData(Object o, Field field, Particle particle) {
        return particle.name();
    }

}
