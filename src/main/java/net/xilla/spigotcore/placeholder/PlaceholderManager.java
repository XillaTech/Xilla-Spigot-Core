package net.xilla.spigotcore.placeholder;

import com.tobiassteely.tobiasapi.api.manager.ManagerEventExecutor;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import net.xilla.spigotcore.player.SpigotPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlaceholderManager extends ManagerParent<Placeholder> {

    public PlaceholderManager() {
        super("XSC.Placeholder", true, "", null);
    }

    public String injectPlaceholders(String string, Player player) {
        for(Placeholder placeholder : new ArrayList<>(getList())) {
            string = placeholder.fill(string, player);
        }

        return string;
    }

}
