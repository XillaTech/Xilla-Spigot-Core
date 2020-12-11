package net.xilla.spigotcore.util.item;

import net.xilla.core.library.manager.Manager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ItemTable extends Manager<String, StoredItem> {

    private String type;

    public ItemTable(String type) {
        super("StoredItems-" + type, "/item-storage/" + type + ".json", StoredItem.class);
        this.type = type;
        load();
    }

    public StoredItem addItem(ItemStack item) {
        String id = UUID.randomUUID().toString().split("-")[0];
        while(getData().containsKey(id)) {
            id = UUID.randomUUID().toString().split("-")[0];
        }

        StoredItem storedItem = new StoredItem(getName(), id, item);
        put(storedItem);
        return storedItem;
    }

    public boolean contains(ItemStack item) {
        for(StoredItem storedItem : iterate()) {
            if(storedItem.isSimilar(item)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(Material type) {
        for(StoredItem storedItem : iterate()) {
            if(storedItem.getItemStack().getType() == type) {
                return true;
            }
        }
        return false;
    }

    public StoredItem get(ItemStack item) {
        for(StoredItem storedItem : iterate()) {
            if(storedItem.isSimilar(item)) {
                return storedItem;
            }
        }
        return null;
    }

    @Override
    protected void objectAdded(StoredItem storedItem) {

    }

    @Override
    protected void objectRemoved(StoredItem storedItem) {

    }

}
