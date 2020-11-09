package net.xilla.spigotcore.controller;

import lombok.Getter;
import lombok.Setter;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.core.library.manager.XillaManager;
import net.xilla.spigotcore.SpigotAPI;
import net.xilla.spigotcore.SpigotException;
import org.json.simple.JSONObject;

public abstract class ControllerObject extends ManagerObject {

    @Getter
    @Setter
    private JSONObject data;

    public ControllerObject(String key, String controller) {
        super(key, XillaManager.getInstance().get(controller));
        this.data = new JSONObject();
    }

    public ControllerObject(String key, JSONObject data, String controller) {
        super(key, XillaManager.getInstance().get(controller));
        this.data = data;
    }

    public void set(String key, Object value) {
        this.data.put(key, value);
    }

    public <T> T get(String key) throws SpigotException {
        if(!data.containsKey(key)) {
            Object def = SpigotAPI.getInstance().getPlayerManager().getDefault(key);
            if(def != null) {
                data.put(key, def);
            } else {
                throw new SpigotException("Missing default data for key " + key, getClass());
            }
        }
        return (T)data.get(key);
    }

    public String getString(String key) throws SpigotException {
        return get(key).toString();
    }

    public int getInt(String key) throws SpigotException {
        return Integer.parseInt(get(key).toString());
    }

    public double getDouble(String key) throws SpigotException {
        return Double.parseDouble(get(key).toString());
    }

    public double getLong(String key) throws SpigotException {
        return Long.parseLong(get(key).toString());
    }

    public float getFloat(String key) throws SpigotException {
        return Float.parseFloat(get(key).toString());
    }

    public boolean contains(String key) {
        return data.containsKey(key);
    }

    public abstract XillaJson getSerializedData();

    public abstract void loadSerializedData(XillaJson xillaJson);

}
