package net.xilla.spigotcore.reflection;

import net.xilla.core.reflection.InvalidReflectionException;
import net.xilla.core.reflection.method.MethodReflection;
import net.xilla.core.reflection.method.MethodReflectionManager;
import net.xilla.core.reflection.storage.StorageReflectionManager;
import net.xilla.spigotcore.reflection.method.Methods1_8;
import net.xilla.spigotcore.reflection.storage.*;
import org.bukkit.Bukkit;

public class SpigotReflection {

    public static Object run(String name, Object... data) throws InvalidReflectionException {
        for(MethodReflection reflection : MethodReflectionManager.getInstance().iterate()) {
            if(Bukkit.getVersion().contains(reflection.getKey().toString())) {
                return reflection.run(name, data);
            }
        }
        throw new InvalidReflectionException("No reflection found");
    }

    public SpigotReflection() {
        if(Bukkit.getVersion().contains("1.8")) {
            MethodReflectionManager.getInstance().put(new Methods1_8());
        }

        try {
            StorageReflectionManager.getInstance().put(new ItemStackReflection());
        } catch (Exception ignored) {}

        try {
            StorageReflectionManager.getInstance().put(new LocationReflection());
        } catch (Exception ignored) {}

        try {
            StorageReflectionManager.getInstance().put(new MaterialReflection());
        } catch (Exception ignored) {}


        try {
            StorageReflectionManager.getInstance().put(new PlayerReflection());
        } catch (Exception ignored) {}

        try {
            Class.forName("Particle");
            ParticleReflection reflection = new ParticleReflection();

            StorageReflectionManager.getInstance().put(reflection);
        } catch (Exception ignored) {}

        try {
            StorageReflectionManager.getInstance().put(new SoundReflection());
        } catch (Exception ignored) {}

        try {
            StorageReflectionManager.getInstance().put(new WorldReflection());
        } catch (Exception ignored) {}

        try {
            StorageReflectionManager.getInstance().put(new SerializedObjectReflection());
        } catch (Exception ignored) {}

    }

}
