package net.xilla.spigotcore.controller;


import net.xilla.core.library.manager.Manager;
import net.xilla.spigotcore.SpigotException;

import java.util.HashMap;
import java.util.Map;

public abstract class Controller<T extends ControllerObject> extends Manager<String, T> {

    private Map<String, Object> defaults;

    protected Controller(String identifier, String settings, Class<T> clazz) {
        super(identifier, settings, clazz);
        this.defaults = new HashMap<>();
    }

    public <Response> Response getDefault(String key) {
        return (Response)defaults.get(key);
    }

    public void setDefault(String key, Object def) {
        defaults.put(key, def);
    }

    public <Response> Response getData(T object, String key) throws SpigotException {
        return (Response)object.get(key);
    }

    public <Response> Response getData(String object, String key) throws SpigotException {
        return getData((T)get(object), key);
    }

    public void setData(T object, String key, Object data) {
        object.set(key, data);
    }

    public void setData(String object, String key, Object data) {
        setData((T)get(object), key, data);
    }

}
