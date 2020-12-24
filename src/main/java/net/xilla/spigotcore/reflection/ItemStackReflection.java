package net.xilla.spigotcore.reflection;

import net.xilla.core.library.config.ConfigFile;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.core.reflection.Reflection;
import net.xilla.spigotcore.util.JsonItemStack;
import net.xilla.spigotcore.util.YamlFile;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.Yaml;

import java.lang.reflect.Field;
import java.util.Map;

public class ItemStackReflection extends Reflection<ItemStack> {

    public ItemStackReflection() {
        super(ItemStack.class);
    }

    @Override
    public ItemStack loadFromSerializedData(ConfigFile file, Object base, Field field, Object o) {

        if(file != null && file.getExtension().equalsIgnoreCase("Yaml")) {
            ConfigurationSection section = (ConfigurationSection) o;
            if(section != null) {
                Map<String, Object> map = section.getValues(true);
                return ItemStack.deserialize(map);
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
