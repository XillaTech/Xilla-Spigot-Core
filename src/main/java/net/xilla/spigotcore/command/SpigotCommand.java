package net.xilla.spigotcore.command;

        import net.xilla.spigotcore.SpigotAPI;
        import org.bukkit.ChatColor;
        import org.bukkit.plugin.java.JavaPlugin;

        import java.util.List;

public class SpigotCommand {

    public SpigotCommand(JavaPlugin plugin, String command, List<String> aliases, CmdExecutor executor) {
        SpigotAPI.getInstance().registerCommand(plugin, command, ((sender, command1, label, args) -> {
            if(label.equalsIgnoreCase(command) || aliases.contains(label.toLowerCase())) {
                return executor.execute(new CommandData(sender, command1, label, args));
            } else {
                return false;
            }
        }));
    }

}
