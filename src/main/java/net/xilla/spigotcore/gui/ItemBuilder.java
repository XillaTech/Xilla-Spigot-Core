package net.xilla.spigotcore.gui;

import net.xilla.spigotcore.SpigotObject;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder implements SpigotObject {

    private ItemStack itemStack;

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material);
    }

    public static ItemBuilder of(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    private ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    private ItemBuilder(ItemStack item) {
        this.itemStack = item;
    }

    public ItemBuilder setLore(String... lore) {
        List<String> coloredLore = new ArrayList<>();

        for(String str : lore) {
            coloredLore.add(colorize(str));
        }

        ItemMeta meta = itemStack.getItemMeta();
        if(meta != null) {
            meta.setLore(coloredLore);
        }
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(colorize(name));

        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }

    public ItemBuilder setType(Material material) {
        this.itemStack.setType(material);
        return this;
    }

    public void enchant(Enchantment enchantment, int level) {
        this.itemStack.addEnchantment(enchantment, level);
    }

}
