package net.xilla.spigotcore.placeholder;

import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.core.library.manager.XillaManager;
import org.bukkit.entity.Player;

public class Placeholder extends ManagerObject {

    private String plugin;
    private String placeholder;
    private PlaceholderExecutor executor;

    public Placeholder(String plugin, String placeholder, PlaceholderExecutor executor) {
        super(plugin + "_" + placeholder, XillaManager.getInstance().get("Spigot_Placeholder"));
        this.plugin = plugin;
        this.placeholder = placeholder;
        this.executor = executor;
    }

    public String fill(String string, Player player) {
        return executor.fill(string, player);
    }

    public String getTag() {
        return getKey().toString();
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getPlugin() {
        return plugin;
    }

    @Override
    public XillaJson getSerializedData() {
        return null;
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {

    }
}
