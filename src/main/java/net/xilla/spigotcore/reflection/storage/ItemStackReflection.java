package net.xilla.spigotcore.reflection.storage;

import net.xilla.core.library.config.ConfigFile;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.core.reflection.storage.StorageReflection;
import net.xilla.spigotcore.util.JsonItemStack;
import net.xilla.spigotcore.util.YamlFile;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.Yaml;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class ItemStackReflection extends StorageReflection<ItemStack> {

    public ItemStackReflection() {
        super(ItemStack.class);
    }

    @Override
    public ItemStack loadFromSerializedData(ConfigFile file, Object base, Field field, Object o) {

        if(file != null && file.getExtension().equalsIgnoreCase("Yaml")) {
            try {
                if(o instanceof MemorySection) {
                    MemorySection section = (MemorySection) o;
                    Map<String, Object> map = section.getValues(true);
                    return ItemStack.deserialize(map);
                } else {
                    LinkedHashMap section = (LinkedHashMap) o;
                    if (section != null) {
                        return ItemStack.deserialize(section);
                    }
                }
            } catch (ClassCastException ex) {
                Map<String, Object> map = (Map<String, Object>) o;
                if(o != null) {
                    return ItemStack.deserialize(map);
                }
            }
        } else {
            return JsonItemStack.fromJson(o.toString());
        }

        return null;
    }

    @Override
    public Object getSerializedData(ConfigFile file, Object base, Field field, ItemStack o) {
        if(file instanceof YamlFile) {
            return o.serialize();
        } else {
            return JsonItemStack.toJson(o);
        }
    }

}
