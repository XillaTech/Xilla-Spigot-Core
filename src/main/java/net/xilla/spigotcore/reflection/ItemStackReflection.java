package net.xilla.spigotcore.reflection;

import net.xilla.core.reflection.Reflection;
import net.xilla.spigotcore.util.JsonItemStack;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.Map;

public class ItemStackReflection extends Reflection<ItemStack> {

    public ItemStackReflection() {
        super(ItemStack.class);
    }

    @Override
    public ItemStack loadFromSerializedData(Object base, Field field, Object o) {
        return ItemStack.deserialize((Map<String, Object>) o);
    }

    @Override
    public Object getSerializedData(Object base, Field field, ItemStack o) {
        return o.serialize();
    }

}
