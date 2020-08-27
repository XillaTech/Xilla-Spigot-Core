package net.xilla.spigotcore.border;

import net.minecraft.server.v1_12_R1.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_12_R1.WorldBorder;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PlayerWorldBorder {

    public static void sendWorldBorder(Player player, Location center, int size){
        WorldBorder border = new WorldBorder();
        border.setCenter(center.getX(), center.getZ());
        border.setSize(size);

        PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(border, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);


        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void removeWorldBorder(Player player){
        WorldBorder border = new WorldBorder();
        border.setCenter(0, 0);
        border.setSize(10000000);

        PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(border, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

}
