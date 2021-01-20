package net.xilla.spigotcore.reflection.method;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.core.reflection.method.Method;
import net.xilla.core.reflection.method.MethodReflection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public class Methods1_8 extends MethodReflection {

    public Methods1_8() {
        super("1.8");

        put("placeBlock", objects -> {
                    Location loc = (Location)objects[0];
                    Material mat = (Material)objects[1];
                    int data = (int)objects[2];
                    // Updated to use native minecraft code (NMS)

                    CraftWorld craftWorld = ((CraftWorld) loc.getWorld());

                    if(craftWorld == null) {
                        Logger.log(LogLevel.INFO, "Unable to place block in empty world!", getClass());
                        return false;
                    }

                    net.minecraft.server.v1_8_R3.World nmsWorld = craftWorld.getHandle();
                    BlockPosition bp = new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());

                    int combined = mat.getId() + (data << 12);

                    IBlockData bd = net.minecraft.server.v1_8_R3.Block.getByCombinedId(combined);;

                    nmsWorld.setTypeAndData(bp, bd,3);
                    return true;
                });
    }

}
