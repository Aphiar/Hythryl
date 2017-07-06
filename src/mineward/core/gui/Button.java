package mineward.core.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Button {

    private Inventory inv;
    private String name;
    private int place;
    private String[] lore;
    private ItemStack is;

    public Button(Inventory inv, String name, String[] lore, ItemStack is,
                  int place) {
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(name);
        List<String> llore = new ArrayList<String>();
        for (String s : lore) {
            llore.add(ChatColor.WHITE + s);
        }
        meta.setLore(llore);
        is.setItemMeta(meta);

        this.inv = inv;
        this.name = name;
        this.lore = lore;
        this.is = is;
        this.place = place;
    }

    public Inventory getInventory() {
        return inv;
    }

    public void setInventory(Inventory inv) {
        this.inv = inv;
    }

    public String getName() {
        return name;
    }

    public String[] getLore() {
        return lore;
    }

    public ItemStack getItem() {
        return is;
    }

    public void setItem(ItemStack is) {
        this.is = is;
    }

    public int getPlace() {
        return place;
    }

    public void show() {
        inv.setItem(getPlace(), getItem());
    }

    public void hide() {
        inv.setItem(getPlace(), new ItemStack(Material.AIR));
    }

}
