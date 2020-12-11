package net.xilla.spigotcore.util.item;

import lombok.Getter;
import lombok.Setter;
import net.xilla.core.library.json.SerializedObject;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.core.library.manager.StoredData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StoredItem extends ManagerObject implements SerializedObject {

    @Getter
    @Setter
    @StoredData
    private ItemStack itemStack = null;

    public StoredItem(String type, String id, ItemStack item) {
        super(id, type);
        this.itemStack = item;
    }

    public StoredItem() {
    }

    public boolean isSimilar(ItemStack item) {

        ItemStack item1 = item;
        ItemStack item2 = this.itemStack;

        if (item2.getType() == item1.getType()) {

            ItemMeta item1Meta = item1.getItemMeta();
            ItemMeta item2Meta = item2.getItemMeta();
            if (item1Meta.hasDisplayName() != item2Meta.hasDisplayName()) {
                return false;
            }
            if (item1Meta.hasDisplayName()) {
                if (!item1Meta.getDisplayName().equals(item2Meta.getDisplayName())) {
                    return false;
                }
            }
            if (item1Meta.hasLore() != item2Meta.hasLore()) {
                return false;
            }
            if (item1Meta.hasLore()) {
                if (item1Meta.getLore().size() != item2Meta.getLore().size()) {
                    return false;
                }
                for (int i = 0; i < item1Meta.getLore().size(); i++) {
                    String lore1 = item1Meta.getLore().get(i);
                    String lore2 = item2Meta.getLore().get(i);
                    if (!lore1.equalsIgnoreCase(lore2)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

}

