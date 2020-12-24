package net.xilla.spigotcore.player;

import net.xilla.spigotcore.SpigotAPI;
import net.xilla.spigotcore.SpigotException;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerAPI {

    public static PlayerAPI instance;

    public PlayerAPI() {
        instance = this;
    }

    public <Response> Response getDefault(String key) {
        return (Response) SpigotAPI.getInstance().getPlayerManager().getDefault(key);
    }

    public void setDefault(String key, Object def) {
        SpigotAPI.getInstance().getPlayerManager().setDefault(key, def);
    }

    public SpigotPlayer get(OfflinePlayer player) throws SpigotException {
        SpigotPlayer spigotPlayer = (SpigotPlayer) SpigotAPI.getInstance().getPlayerManager().get(player.getUniqueId().toString());
        if(spigotPlayer == null) {
            spigotPlayer = new SpigotPlayer(player.getUniqueId().toString(), player.getName());
            SpigotAPI.getInstance().getPlayerManager().put(spigotPlayer);
        }
        return spigotPlayer;
    }

    public <T> T getData(SpigotPlayer object, String key) throws SpigotException {
        return (T)object.get(key);
    }

    public <T> T getData(Player object, String key) throws SpigotException {
        return getData(get(object), key);
    }

    public void setData(SpigotPlayer object, String key, Object data) {
        object.set(key, data);
    }

    public void setData(Player object, String key, Object data) {
        try {
            setData(get(object), key, data);
        } catch (SpigotException e) {
            e.printStackTrace();
        }
    }

}
