package net.xilla.spigotcore.block;

import lombok.Getter;
import net.xilla.core.reflection.InvalidReflectionException;
import net.xilla.spigotcore.reflection.SpigotReflection;
import org.bukkit.Location;
import org.bukkit.Material;

public class QueuedBlock {

    @Getter
    private Material material;

    @Getter
    private Location location;

    @Getter
    private int data;

    public QueuedBlock(Location location, Material material, int data) {
        this.location = location;
        this.material = material;
        this.data = data;
    }

    public boolean place() {
        try {
            return (boolean) SpigotReflection.run("placeBlock", location, material, data);
        } catch (InvalidReflectionException e) {
            e.printStackTrace();
            return false;
        }
    }

}
