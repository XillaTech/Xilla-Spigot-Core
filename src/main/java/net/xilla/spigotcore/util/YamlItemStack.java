package net.xilla.spigotcore.util;

import lombok.Getter;
import net.xilla.core.library.json.SerializedObject;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.spigotcore.SpigotAPI;
import net.xilla.spigotcore.gui.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamlItemStack implements SerializedObject {

    @Getter
    private ItemStack item;

    public YamlItemStack(ItemStack item) {
        this.item = item;
    }

    public YamlItemStack() {}

    @Override
    public XillaJson getSerializedData() {
        XillaJson json = new XillaJson();

        json.put("material", item.getType().name());

        if(item.getAmount() != 1) {
            json.put("amount", item.getAmount());
        }

        if(item.getData() != null) {
            if(item.getData().getData() != 0) {
                json.put("data", item.getData().getData());
            }
        }

        ItemMeta im = item.getItemMeta();
        if(im != null) {
            if(im.hasDisplayName()) {
               json.put("name", im.getDisplayName());
            }
            if(im.hasLore()) {
                json.put("lore", im.getLore());
            }
            if(im.hasEnchants()) {
                Map<String, Integer> enchants = new HashMap<>();

                for(Enchantment ench : im.getEnchants().keySet()) {
                    enchants.put(ench.getKey().getKey(), im.getEnchantLevel(ench));
                }

                json.put("enchantments", enchants);
            }
        }

        return json;
    }

    @Override
    public void loadSerializedData(XillaJson json) {
        Material mat = Material.valueOf(json.get("material"));
        byte data = 0;
        int amount = 1;

        if(json.containsKey("data")) {
            data = Byte.parseByte(json.get("data").toString());
        }

        if(json.containsKey("amount")) {
            amount = Integer.parseInt(json.get("amount").toString());
        }

        ItemBuilder builder = ItemBuilder.of(new ItemStack(mat, amount, data));

        if(json.containsKey("name")) {
            builder.setName(json.get("name"));
        }

        if(json.containsKey("lore")) {
            List<String> lore = json.get("lore");
            builder.setLore(lore.toArray(new String[0]));
        }

        if(json.containsKey("enchantments")) {
            Map<String, Integer> enchants = json.get("enchantments");

            for(String key : enchants.keySet()) {
                Enchantment enchantment = EnchantmentWrapper.getByKey(NamespacedKey.minecraft(key));
                builder.enchant(enchantment, enchants.get(key));
            }
        }

        item = builder.build();
    }
}
