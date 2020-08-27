package net.xilla.spigotcore.command;

        import net.xilla.spigotcore.log.SpigotLogger;
        import org.bukkit.plugin.java.JavaPlugin;

        import java.util.List;
        import java.util.Objects;

public class SpigotCommand {

    public SpigotCommand(JavaPlugin plugin, String command, List<String> aliases, CmdExecutor executor) {
        Objects.requireNonNull(plugin.getCommand(command)).setExecutor(((sender, command1, label, args) -> {
            if(label.equalsIgnoreCase(command) || aliases.contains(label.toLowerCase())) {
                try {
                    return executor.execute(new CommandData(sender, command1, label, args));
                } catch (Exception e) {
                    SpigotLogger.logException(e);
                    return true;
                }
            } else {
                return false;
            }
        }));
    }

}
