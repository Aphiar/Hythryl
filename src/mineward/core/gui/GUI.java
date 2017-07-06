package mineward.core.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GUI {

    public String name;
    public int size;
    public List<Button> buttons = new ArrayList<Button>();
    public Inventory inv;

    public GUI(String name, int size) {
        this.name = name;
        this.size = size;
        inv = Bukkit.createInventory(null, size, name);
    }

    public void AddButton(Button button) {
        buttons.add(button);
    }

    public void show(Player p) {
        for (Button button : buttons) {
            button.setInventory(inv);
            button.show();
        }
        p.openInventory(inv);
    }

    public static GUI createGUI(String name, int size) {
        return new GUI(name, size);
    }

}