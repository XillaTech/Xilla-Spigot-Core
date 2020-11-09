package net.xilla.spigotcore.border;

import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_8_R3.WorldBorder;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PlayerWorldBorder1_8 {

    /**
     * This command only works on 1.16.3
     */
    public static void sendWorldBorder(Player player, Location center, int size) {
        WorldBorder border = new WorldBorder();
        border.setCenter(center.getX(), center.getZ());
        border.setSize(size);

        PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(border, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    /**
     * This command only works on 1.16.3
     */
    public static void removeWorldBorder(Player player)  {
        WorldBorder border = new WorldBorder();
        border.setCenter(0, 0);
        border.setSize(10000000);

        PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(border, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

}
