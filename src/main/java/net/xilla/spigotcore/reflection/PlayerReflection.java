package net.xilla.spigotcore.reflection;

import net.xilla.core.library.config.ConfigFile;
import net.xilla.core.reflection.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.UUID;

public class PlayerReflection extends Reflection<Player> {

    public PlayerReflection() {
        super(Player.class);
    }

    @Override
    public Player loadFromSerializedData(ConfigFile configFile, Object o, Field field, Object o1) {
        return Bukkit.getOfflinePlayer(UUID.fromString(o1.toString())).getPlayer();
    }

    @Override
    public Object getSerializedData(ConfigFile configFile, Object o, Field field, Player player) {
        return player.getUniqueId().toString();
    }
}
