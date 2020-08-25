package net.xilla.spigotcore;

import com.tobiassteely.tobiasapi.TobiasAPI;
import com.tobiassteely.tobiasapi.api.TobiasObject;
import org.bukkit.ChatColor;
import org.bukkit.Server;

public class SpigotObject extends TobiasObject {

    public SpigotCore getCore() {
        return SpigotCore.getInstance();
    }

    public TobiasAPI getAPI() {
        return SpigotCore.getInstance().getApi();
    }

    public Server getServer() {
        return SpigotCore.getInstance().getServer();
    }

    public String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

}
