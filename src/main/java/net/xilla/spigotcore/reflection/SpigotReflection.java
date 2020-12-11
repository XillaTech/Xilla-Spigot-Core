package net.xilla.spigotcore.reflection;

import net.xilla.core.reflection.ReflectionManager;

public class SpigotReflection {

    public SpigotReflection() {
        try {
            ReflectionManager.getInstance().put(new ItemStackReflection());
        } catch (Exception ignored) {}

        try {
            ReflectionManager.getInstance().put(new LocationReflection());
        } catch (Exception ignored) {}

        try {
            ReflectionManager.getInstance().put(new MaterialReflection());
        } catch (Exception ignored) {}

        try {
            Class.forName("Particle");
            ParticleReflection reflection = new ParticleReflection();

            ReflectionManager.getInstance().put(reflection);
        } catch (Exception ignored) {}

        try {
            ReflectionManager.getInstance().put(new SoundReflection());
        } catch (Exception ignored) {}
    }

}
