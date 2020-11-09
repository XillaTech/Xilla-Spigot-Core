package net.xilla.spigotcore.manager;

import net.xilla.core.library.json.SerializedObject;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.core.library.manager.StoredData;
import net.xilla.core.library.manager.XillaManager;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public class MCObject extends ManagerObject {

    public MCObject() {
    }

    public MCObject(String key, Manager manager) {
        super(key, manager);
    }

    public MCObject(String key, String manager) {
        super(key, manager);
    }

    @Override
    public XillaJson getSerializedData() {
        XillaJson json = new XillaJson();

        Class<?> c = this.getClass();

        try {
            for(Field field : getClass().getDeclaredFields()) {
                if (field.getAnnotation(StoredData.class) != null) {
                    Object object;
                    if(!field.isAccessible()) {
                        field.setAccessible(true);

                        object = field.get(this);

                        field.setAccessible(false);
                    } else {
                        object = field.get(this);
                    }

                    if(object instanceof SerializedObject) {
                        json.put(field.getName(), ((SerializedObject)object).getSerializedData().getJson());
                    } if(object instanceof ConfigurationSerializable) {
                        json.put(field.getName(), ((Location)object).serialize());
                    } else {
                        json.put(field.getName(), object);
                    }
                }
            }
            json.put("key", getKey());
            json.put("manager", getManager().getKey());
            return json;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void loadSerializedData(XillaJson json) {
        setKey(json.get("key"));
        if(json.containsKey("manager")) {
            setManager(XillaManager.getInstance().get(json.get("manager")));
        }
        for(Field field : getClass().getDeclaredFields()) {
            if (field.getAnnotation(StoredData.class) != null) {
                if(json.containsKey(field.getName())) {
                    try {
                        if(!field.isAccessible()) {
                            field.setAccessible(true);

                            Object object = field.get(this);

                            if(field.getType().isAssignableFrom(SerializedObject.class)) {
                                ((SerializedObject) object).loadSerializedData(new XillaJson(json.get(field.getName())));
                            } else if(field.getType().isAssignableFrom(ItemStack.class)) {
                                field.set(this, ItemStack.deserialize(json.get(field.getName())));
                            } else if(field.getType().isAssignableFrom(Location.class)) {
                                field.set(this, Location.deserialize(json.get(field.getName())));
                            } else {
                                field.set(this, json.get(field.getName()));
                            }

                            field.setAccessible(false);
                        } else {
                            Object object = field.get(this);

                            if(field.getType().isAssignableFrom(SerializedObject.class)) {
                                ((SerializedObject) object).loadSerializedData(new XillaJson(json.get(field.getName())));
                            } else if(field.getType().isAssignableFrom(ItemStack.class)) {
                                field.set(this, ItemStack.deserialize(json.get(field.getName())));
                            } else if(field.getType().isAssignableFrom(Location.class)) {
                                field.set(this, Location.deserialize(json.get(field.getName())));
                            } else {
                                field.set(this, json.get(field.getName()));
                            }
//                            if(object instanceof SerializedObject) {
//                                ((SerializedObject) object).loadSerializedData(new XillaJson(json.get(field.getName())));
//                            } if(object instanceof ItemStack) {
//                                field.set(this, ItemStack.deserialize(json.get(field.getName())));
//                            } if(object instanceof Location) {
//                                field.set(this, Location.deserialize(json.get(field.getName())));
//                            } else {
//                                field.set(this, json.get(field.getName()));
//                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
