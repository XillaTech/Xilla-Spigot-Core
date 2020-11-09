package net.xilla.spigotcore.placeholder;

import net.xilla.core.library.manager.Manager;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlaceholderManager extends Manager<Placeholder> {

    public PlaceholderManager() {
        super("Spigot_Placeholder");
    }

    public String injectPlaceholders(String string, Player player) {
        for(Placeholder placeholder : new ArrayList<>(getData().values())) {
            string = placeholder.fill(string, player);
        }

        return string;
    }

    @Override
    public void load() {

    }

    @Override
    protected void objectAdded(Placeholder placeholder) {

    }

    @Override
    protected void objectRemoved(Placeholder placeholder) {

    }

}
