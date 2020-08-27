package net.xilla.spigotcore.log;

import com.tobiassteely.tobiasapi.api.log.Logger;
import net.xilla.spigotcore.SpigotAPI;
import net.xilla.spigotcore.SpigotException;

import java.io.FileWriter;
import java.io.IOException;

public class SpigotLogger implements Logger {

    public static void logCoreException(String plugin, SpigotException exception) {
        StringBuilder stringBuilder = new StringBuilder();
        for(StackTraceElement element : exception.getStackTrace()) {
            stringBuilder.append(element.toString());
        }

        SpigotAPI.getAPI().getLog().sendMessage(Logger.level_error, plugin, "Error in class " + exception.getClazz().getPackageName() + "."  + exception.getClazz().getName() + "\n" + stringBuilder.toString());
    }

    public static void logCoreException(SpigotException exception) {
        StringBuilder stringBuilder = new StringBuilder();
        for(StackTraceElement element : exception.getStackTrace()) {
            stringBuilder.append(element.toString());
        }

        SpigotAPI.getAPI().getLog().sendMessage(Logger.level_error, "Error in class " + exception.getClazz().getPackageName() + "."  + exception.getClazz().getName() + "\n" + stringBuilder.toString());
    }

    public static void logException(String plugin, Exception exception) {
        StringBuilder stringBuilder = new StringBuilder();
        for(StackTraceElement element : exception.getStackTrace()) {
            stringBuilder.append(element.toString());
        }

        SpigotAPI.getAPI().getLog().sendMessage(Logger.level_error, plugin, stringBuilder.toString());
    }

    public static void logException(Exception exception) {
        StringBuilder stringBuilder = new StringBuilder();
        for(StackTraceElement element : exception.getStackTrace()) {
            stringBuilder.append(element.toString());
        }

        SpigotAPI.getAPI().getLog().sendMessage(Logger.level_error, stringBuilder.toString());
    }

    @Override
    public void log(int i, String plugin, String message) {
        if(i == Logger.level_info) {
            SpigotAPI.getCore().getLogger().info("[" + plugin + "] " + message);
        } else if(i == Logger.level_warn) {
            String finalmessage = "[" + plugin + "] " + message;
            SpigotAPI.getCore().getLogger().warning(finalmessage);
            try {
                FileWriter writer = new FileWriter(SpigotAPI.getCore().getDataFolder() + "errors.txt");

                finalmessage = finalmessage + "\n";
                for(char c : finalmessage.toCharArray()) {
                    writer.append(c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(i == Logger.level_critical) {
            String finalmessage = "[" + plugin + "] [CRITICAL!] " + message;
            SpigotAPI.getCore().getLogger().severe(finalmessage);
            try {
                FileWriter writer = new FileWriter(SpigotAPI.getCore().getDataFolder() + "errors.txt");

                finalmessage = finalmessage + "\n";
                for(char c : finalmessage.toCharArray()) {
                    writer.append(c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(i == Logger.level_error) {
            String finalmessage = "[" + plugin + "] " + message;
            SpigotAPI.getCore().getLogger().severe(finalmessage);
            try {
                FileWriter writer = new FileWriter(SpigotAPI.getCore().getDataFolder() + "errors.txt");

                finalmessage = finalmessage + "\n";
                for(char c : finalmessage.toCharArray()) {
                    writer.append(c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void log(int i, String message) {
        if(i == Logger.level_info) {
            SpigotAPI.getCore().getLogger().info("[Spigot Core] " + message);
        } else if(i == Logger.level_warn) {
            SpigotAPI.getCore().getLogger().warning(message);
            try {
                FileWriter writer = new FileWriter(SpigotAPI.getCore().getDataFolder() + "errors.txt");

                message = message + "\n";
                for(char c : message.toCharArray()) {
                    writer.append(c);
                }
            } catch (IOException e) {
                SpigotLogger.logException(e);
            }
        } else if(i == Logger.level_critical) {
            SpigotAPI.getCore().getLogger().severe("[Spigot Core] [CRITICAL] " + message);
            try {
                FileWriter writer = new FileWriter(SpigotAPI.getCore().getDataFolder() + "errors.txt");

                message = message + "\n";
                for(char c : message.toCharArray()) {
                    writer.append(c);
                }
            } catch (IOException e) {
                SpigotLogger.logException(e);
            }
        } else if(i == Logger.level_error) {
            SpigotAPI.getCore().getLogger().severe("[Spigot Core] " + message);
            try {
                FileWriter writer = new FileWriter(SpigotAPI.getCore().getDataFolder() + "errors.txt");

                message = message + "\n";
                for(char c : message.toCharArray()) {
                    writer.append(c);
                }
            } catch (IOException e) {
                SpigotLogger.logException(e);
            }
        }
    }

}
