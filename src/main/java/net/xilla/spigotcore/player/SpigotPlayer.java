package net.xilla.spigotcore.player;

import net.xilla.core.library.json.XillaJson;
import net.xilla.spigotcore.controller.ControllerObject;
import org.bukkit.configuration.MemorySection;
import org.json.simple.JSONObject;

public class SpigotPlayer extends ControllerObject {

    private String lastName;

    public SpigotPlayer(String uuid, String lastName) {
        super(uuid, "Spigot_Player");
        this.lastName = lastName;
    }

    public SpigotPlayer() {}

    public String getLastName() {
        return lastName;
    }

    @Override
    public XillaJson getSerializedData() {
        return new XillaJson().put("data", getData()).put("lastName", lastName);
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {

        Object obj = xillaJson.get("data");

        if(obj instanceof MemorySection) {
            MemorySection sect = (MemorySection) obj;
            setData(new JSONObject(sect.getValues(false)));
        } else {
            setData(new JSONObject(xillaJson.get("data")));
        }

        this.lastName = xillaJson.get("lastName");
    }
}
