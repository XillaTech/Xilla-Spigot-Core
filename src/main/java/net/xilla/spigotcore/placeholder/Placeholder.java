package net.xilla.spigotcore.placeholder;

import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import org.bukkit.entity.Player;

public class Placeholder extends ManagerObject {

    private String plugin;
    private String placeholder;
    private PlaceholderExecutor executor;

    public Placeholder(String plugin, String placeholder, PlaceholderExecutor executor) {
        super(plugin + "_" + placeholder);
        this.plugin = plugin;
        this.placeholder = placeholder;
        this.executor = executor;
    }

    public String fill(String string, Player player) {
        return executor.fill(string, player);
    }

    public String getTag() {
        return getKey();
    }


}
