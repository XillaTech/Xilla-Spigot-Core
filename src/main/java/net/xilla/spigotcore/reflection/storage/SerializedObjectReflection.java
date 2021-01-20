package net.xilla.spigotcore.reflection.storage;

import net.xilla.core.library.config.ConfigFile;
import net.xilla.core.library.json.SerializedObject;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.core.reflection.storage.StorageReflection;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.util.Map;

public class SerializedObjectReflection extends StorageReflection<SerializedObject> {

    public SerializedObjectReflection() {
        super(SerializedObject.class);
    }

    @Override
    public SerializedObject loadFromSerializedData(ConfigFile file, Object obj, Field field, Object object) {
        if(file.getExtension().equalsIgnoreCase("Yaml")) {
            ConfigurationSection section = (ConfigurationSection) object;
            if (section != null) {

                try {
                    SerializedObject so = (SerializedObject) field.get(obj);
                    so.loadSerializedData(new XillaJson(fromMemorySection(section)));
                    return so;
                } catch (IllegalAccessException e) {
                    Logger.log(LogLevel.ERROR, "Error while loading data from serialized object " + field.getName(), getClass());
                    e.printStackTrace();
                }
            }
        } else {
            try {
                SerializedObject so = (SerializedObject) field.get(obj);
                so.loadSerializedData(new XillaJson(new JSONObject((Map) object)));
                return so;
            } catch (IllegalAccessException e) {
                Logger.log(LogLevel.ERROR, "Error while loading data from serialized object " + field.getName(), getClass());
                e.printStackTrace();
            }
        }
        return null;
    }

    public JSONObject fromMemorySection(ConfigurationSection section) {
        Map<String, Object> map = section.getValues(false);

        JSONObject jsonObject = new JSONObject(map);
//        for(String key : map.keySet()) {
//            JSONObject layer = jsonObject;
//            String[] temp = key.split("\\.");
//            for(int i = 0; i < temp.length; i++) {
//                String k = temp[i];
//
//                if(i == temp.length - 1) {
//                    // last layer
//                    layer.put(k, map.get(key));
//                } else {
//                    // pre-layers
//
//
//
////                    if(key.startsWith(k)) {
////                        layer = jsonObject;
////                    } else {
////                        if(!layer.containsKey(k)) {
////                            layer.put(k, new JSONObject());
////                        }
////                        layer = (JSONObject) layer.get(k);
////                    }
//                }
//            }
//        }
        return jsonObject;
    }

    @Override
    public Object getSerializedData(ConfigFile file, Object obj, Field field, SerializedObject object) {
        return object.getSerializedData().getJson();
    }
}
