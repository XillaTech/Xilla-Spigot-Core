package net.xilla.spigotcore.gui;

import lombok.Getter;
import lombok.Setter;
import net.xilla.spigotcore.SpigotAPI;
import net.xilla.spigotcore.SpigotObject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiInventory extends SpigotObject implements Listener {

    private static Map<HumanEntity, GuiInventory> openInventories = new HashMap<>();

    @Getter
    private Map<Integer, GuiItem> items = new HashMap<>();
    @Getter
    private String name;
    @Getter
    private final Inventory inv;

    private int lastSlot = -1;

    public GuiInventory(String name, int size) {
        this.name = name;
        this.items = new HashMap<>();

        this.inv = Bukkit.createInventory(null, size, colorize(name));

        SpigotAPI.getInstance().getPlugin().getServer().getPluginManager().registerEvents(this, SpigotAPI.getInstance().getPlugin());
    }

    public void setItem(int slot, GuiItem item) {
        items.put(slot, item);
        inv.setItem(slot, item.getItemStack());
        lastSlot = slot;
    }

    public void addItem(GuiItem item) {
        lastSlot++;
        inv.setItem(lastSlot, item.getItemStack());
        items.put(lastSlot, item);
    }

    public void openMenu(HumanEntity player, Object... args) {
        openInventories.put(player, this);
        openInventory(player);
    }

    public void closeMenu(HumanEntity player) {
        openInventories.remove(player, this);
    }


    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    public void addBackButton() {
        setItem(getInv().getSize() - 5, new GuiItem(ItemBuilder.of(Material.BARRIER).build()));
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() != inv) return;

        e.setCancelled(true);

        int slot = e.getRawSlot();
        GuiItem item = getItems().get(slot);
        if(item != null) {
            item.click(e);
        }
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(InventoryDragEvent e) {
        if (e.getInventory() == inv) {
            e.setCancelled(true);
        }
    }

    // Remove player from cache
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory() == inv) {
            closeMenu(e.getPlayer());
        }
    }

}
