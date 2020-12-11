package net.xilla.spigotcore;

import net.xilla.core.log.Logger;

public class SpigotException extends Exception {

    private Class clazz = null;

    public SpigotException(String message, Class clazz) {
        super(message);
        this.clazz = clazz;

        Logger.log(this, this.clazz);
    }

    public SpigotException(String plugin, String message, Class clazz) {
        super(message);
        this.clazz = clazz;

        Logger.log(this, this.clazz);
    }

    public Class getClazz() {
        return clazz;
    }

}
