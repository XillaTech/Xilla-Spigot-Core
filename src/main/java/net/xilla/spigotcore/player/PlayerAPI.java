package net.xilla.spigotcore.player;

import net.xilla.spigotcore.SpigotAPI;
import net.xilla.spigotcore.SpigotException;
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

    public SpigotPlayer get(Player player) throws SpigotException {
        SpigotPlayer spigotPlayer = (SpigotPlayer) SpigotAPI.getInstance().getPlayerManager().get(player.getUniqueId().toString());
        if(spigotPlayer == null) {
            spigotPlayer = new SpigotPlayer(player.getUniqueId().toString(), player.getDisplayName());
            SpigotAPI.getInstance().getPlayerManager().put(spigotPlayer);
        }
        return spigotPlayer;
    }

    public <Response> Response getData(SpigotPlayer object, String key) throws SpigotException {
        return (Response)object.get(key);
    }

    public <Response> Response getData(String object, String key) throws SpigotException {
        return getData((SpigotPlayer) SpigotAPI.getInstance().getPlayerManager().get(object), key);
    }

    public void setData(SpigotPlayer object, String key, Object data) {
        object.set(key, data);
    }

    public void setData(String object, String key, Object data) {
        setData((SpigotPlayer) SpigotAPI.getInstance().getPlayerManager().get(object), key, data);
    }

}
