package net.xilla.spigotcore.command;

import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.spigotcore.SpigotAPI;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class SpigotCommand extends CommandLibrary {

    public SpigotCommand(JavaPlugin plugin, String command, List<String> aliases, CmdExecutor executor) {
        if(plugin == null) {
            Logger.log(LogLevel.FATAL, "Command is missing it's plugin data!", getClass());
            return;
        }
        PluginCommand cmd = plugin.getCommand(command);
        if(cmd != null) {
            cmd.setExecutor(((sender, command1, label, args) -> {
                if (label.equalsIgnoreCase(command) || aliases.contains(label.toLowerCase())) {
                    try {
                        return executor.execute(new CommandData(sender, command1, label, args));
                    } catch (Exception e) {
                        Logger.log(e, this.getClass());
                        return true;
                    }
                } else {
                    return false;
                }
            }));
        } else {
            Logger.log(LogLevel.WARN, "Command " + command + " is not in the plugin.yml!", getClass());
        }
    }

    public SpigotCommand(String command, List<String> aliases, CmdExecutor executor) {
        if(SpigotAPI.getInstance().getPlugin() == null) {
            Logger.log(LogLevel.FATAL, "Command is missing it's plugin data!", getClass());
            return;
        }
        PluginCommand cmd = SpigotAPI.getInstance().getPlugin().getCommand(command);
        if(cmd != null) {
            cmd.setExecutor(((sender, command1, label, args) -> {
                if (label.equalsIgnoreCase(command) || aliases.contains(label.toLowerCase())) {
                    try {
                        return executor.execute(new CommandData(sender, command1, label, args));
                    } catch (Exception e) {
                        Logger.log(e, this.getClass());
                        return true;
                    }
                } else {
                    return false;
                }
            }));
        } else {
            Logger.log(LogLevel.WARN, "Command " + command + " is not in the plugin.yml!", getClass());
        }
    }

    public SpigotCommand(JavaPlugin plugin, String command, CmdExecutor executor) {
        if(plugin == null) {
            Logger.log(LogLevel.FATAL, "Command is missing it's plugin data!", getClass());
            return;
        }
        PluginCommand cmd = plugin.getCommand(command);
        if(cmd != null) {
            cmd.setExecutor(((sender, command1, label, args) -> {
                if (label.equalsIgnoreCase(command)) {
                    try {
                        return executor.execute(new CommandData(sender, command1, label, args));
                    } catch (Exception e) {
                        Logger.log(e, this.getClass());
                        return true;
                    }
                } else {
                    return false;
                }
            }));
        } else {
            Logger.log(LogLevel.WARN, "Command " + command + " is not in the plugin.yml!", getClass());
        }
    }

    public SpigotCommand(String command, CmdExecutor executor) {
        if(SpigotAPI.getInstance().getPlugin() == null) {
            Logger.log(LogLevel.FATAL, "Command is missing it's plugin data!", getClass());
            return;
        }
        PluginCommand cmd = SpigotAPI.getInstance().getPlugin().getCommand(command);
        if(cmd != null) {
            cmd.setExecutor(((sender, command1, label, args) -> {
                if (label.equalsIgnoreCase(command)) {
                    try {
                        return executor.execute(new CommandData(sender, command1, label, args));
                    } catch (Exception e) {
                        Logger.log(e, this.getClass());
                        return true;
                    }
                } else {
                    return false;
                }
            }));
        } else {
            Logger.log(LogLevel.WARN, "Command " + command + " is not in the plugin.yml!", getClass());
        }
    }

}
