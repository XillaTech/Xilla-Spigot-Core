package net.xilla.spigotcore.gui;

import lombok.Getter;
import lombok.Setter;
import net.xilla.spigotcore.SpigotObject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiItem implements SpigotObject {

    @Getter
    @Setter
    private ItemStack itemStack;

    @Setter
    @Getter
    private GuiButtonExecutor executor;

    public GuiItem(ItemStack itemStack, GuiButtonExecutor executor) {
        this.itemStack = itemStack;
        this.executor = executor;
    }

    public GuiItem(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.executor = null;
    }

    public void click(InventoryClickEvent e) {
        if(this.executor != null) {
            this.executor.click(e);
        }
    }

}
