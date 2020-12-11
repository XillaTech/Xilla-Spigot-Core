package net.xilla.spigotcore.gui;

import lombok.Getter;
import lombok.Setter;
import net.xilla.spigotcore.SpigotAPI;
import net.xilla.spigotcore.SpigotObject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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

public class GuiInventory implements SpigotObject, Listener {

    private static Map<HumanEntity, GuiInventory> openInventories = new HashMap<>();

    @Getter
    private Map<Integer, GuiItem> items;
    @Getter
    private String name;
    @Getter
    private final Inventory inv;
    @Getter
    @Setter
    private boolean canMoveEmptyItems = false;

    private int lastSlot = -1;

    public GuiInventory(String name, int size) {
        this.name = name;
        this.items = new HashMap<>();

        this.inv = Bukkit.createInventory(null, size, colorize(name));
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

        SpigotAPI.getInstance().getPlugin().getServer().getPluginManager().registerEvents(this, SpigotAPI.getInstance().getPlugin());

        openInventories.put(player, this);
        openInventory(player);

    }

    public void closeMenu(HumanEntity player) {
        openInventories.remove(player, this);

        InventoryClickEvent.getHandlerList().unregister(this);
        InventoryClickEvent.getHandlerList().unregister(this);
    }

    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    public void addBackButton() {
        setItem(getInv().getSize() - 5, new GuiItem(ItemBuilder.of(Material.BARRIER).build()));
    }

    // Check for clicks on items
    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(final InventoryClickEvent e) {

        if (!e.getInventory().equals(inv)) return;

        boolean cancelled = true;

        int slot = e.getRawSlot();
        GuiItem item = getItems().get(slot);

        if(item != null) {

            if(canMoveEmptyItems && item.getExecutor() == null) {
                cancelled = false;
            }

            item.click(e);
        } else if(canMoveEmptyItems) {
            cancelled = false;
        }

        if(cancelled) {
            e.setResult(Event.Result.DENY);
            //e.setCancelled(true);
        }
    }

    // Remove player from cache
    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (e.getInventory().equals(inv)) {
            closeMenu(e.getPlayer());
        }
    }

    public void setRow(int row, GuiItem item) {
        for(int i = 9 * row; i < 9 * (row + 1); i++) {
            setItem(i, item);
        }
    }

    public void setColumn(int col, GuiItem item) {
        for(int i = 0; i < inv.getSize() / 9; i++) {
            int slot = (i * 9) + col;
            setItem(slot, item);
        }
    }

}
