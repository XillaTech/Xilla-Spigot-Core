package net.xilla.spigotcore.gui;

import lombok.Getter;
import lombok.Setter;
import net.xilla.spigotcore.SpigotAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ListGui extends GuiInventory {

    private int page = 0;

    private List<GuiItem> items = new ArrayList<>();

    @Getter
    @Setter
    private ItemStack fillerItem = ItemBuilder.of(Material.AIR).build();

    @Getter
    @Setter
    private ItemStack borderItem = ItemBuilder.of(Material.AIR).build();

    @Getter
    @Setter
    private ItemStack backButton = ItemBuilder.of(Material.ARROW).setName("&6Go Back").setLore("&7Click to go back.").build();

    @Getter
    @Setter
    private ItemStack forwardButton = ItemBuilder.of(Material.ARROW).setName("&6Go Forward").setLore("&7Click to go forward.").build();

    public ListGui(String name, List<GuiItem> items, int size) {
        super(name, size);

        this.items.addAll(items);
    }

    public void loadPage() {

        for(int i = 0; i < getInv().getSize(); i++) {
            setItem(i, new GuiItem(fillerItem));
        }

        setRow(0, new GuiItem(borderItem));
        setRow(((getInv().getSize() / 9) - 1), new GuiItem(borderItem));
        setColumn(0, new GuiItem(borderItem));
        setColumn(8, new GuiItem(borderItem));

        int offset = page * ((getInv().getSize() / 9) - 2) * 7;
        int item = offset;
        for(int y = 0; y < ((getInv().getSize() / 9) - 2); y++) {
            for(int x = 0; x < 7; x++) {
                if(items.size() <= item) {
                    break;
                }
                setItem(toSlot(y, x), items.get(item));
                item++;
            }
        }

        if(page > 0) {
            setItem(getInv().getSize() - 6, new GuiItem(backButton, (event) -> {
                page = page - 1;
                event.getWhoClicked().closeInventory();
                Bukkit.getScheduler().scheduleSyncDelayedTask(SpigotAPI.getInstance().getPlugin(), () -> openMenu(event.getWhoClicked()), 1);
            }));
        }

        if(items.size() > offset + ((getInv().getSize() / 9) - 2) * 7) {
            setItem(getInv().getSize() - 4, new GuiItem(forwardButton, (event) -> {
                page = page + 1;
                event.getWhoClicked().closeInventory();
                Bukkit.getScheduler().scheduleSyncDelayedTask(SpigotAPI.getInstance().getPlugin(), () -> openMenu(event.getWhoClicked()), 1);
            }));
        }
    }

    public int toSlot(int row, int col) {
        return ((row + 1) * 9) + (col + 1);
    }

    @Override
    public void openMenu(HumanEntity player, Object... args) {
        loadPage();
        super.openMenu(player, args);
    }

}
