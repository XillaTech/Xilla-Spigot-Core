package net.xilla.spigotcore.player;

import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.spigotcore.SpigotAPI;
import net.xilla.spigotcore.controller.Controller;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class PlayerManager extends Controller<SpigotPlayer> {

    public PlayerManager() {
        super("Spigot_Player", SpigotAPI.getInstance().getPlugin().getDataFolder() + "/player-data.json");
        load();
    }

    @Override
    public void load() {
        if(getConfig() != null) {
            for(Object key : new ArrayList<Object>(getConfig().getJson().getJson().keySet())) {
                JSONObject json = getConfig().getJSON(key.toString());
                put(new SpigotPlayer(key.toString(), new XillaJson(json)));
            }
        }
    }

    @Override
    protected void objectAdded(ManagerObject managerObject) {

    }

    @Override
    protected void objectRemoved(ManagerObject managerObject) {

    }

}
