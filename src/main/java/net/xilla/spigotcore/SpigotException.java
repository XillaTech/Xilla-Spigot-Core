package net.xilla.spigotcore;

import net.xilla.spigotcore.log.SpigotLogger;

public class SpigotException extends Exception {

    private Class clazz = null;

    public SpigotException(String message, Class clazz) {
        super(message);
        this.clazz = clazz;

        SpigotLogger.logCoreException(this);
    }

    public SpigotException(String plugin, String message, Class clazz) {
        super(message);
        this.clazz = clazz;

        SpigotLogger.logCoreException(plugin, this);
    }

    public Class getClazz() {
        return clazz;
    }

}
