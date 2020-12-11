package net.xilla.spigotcore.player;

import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.spigotcore.SpigotAPI;
import net.xilla.spigotcore.controller.Controller;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class PlayerManager extends Controller<SpigotPlayer> {

    public PlayerManager() {
        super("Spigot_Player","player-data.yml", SpigotPlayer.class);
        load();
    }

}
