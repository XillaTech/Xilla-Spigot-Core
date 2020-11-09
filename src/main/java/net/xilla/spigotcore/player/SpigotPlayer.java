package net.xilla.spigotcore.player;

import net.xilla.core.library.json.XillaJson;
import net.xilla.spigotcore.controller.ControllerObject;

public class SpigotPlayer extends ControllerObject {

    private String lastName;

    public SpigotPlayer(String uuid, XillaJson data) {
        super(uuid, "Spigot_Player");
        loadSerializedData(data);
    }

    public SpigotPlayer(String uuid, String lastName) {
        super(uuid, "Spigot_Player");
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public XillaJson getSerializedData() {
        return new XillaJson().put("data", getData()).put("lastName", lastName);
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {
        setData(xillaJson.get("data"));
        this.lastName = xillaJson.get("lastName");
    }
}
