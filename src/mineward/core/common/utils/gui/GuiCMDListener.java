package mineward.core.common.utils.gui;

import mineward.core.gui.Button;
import mineward.core.listener.MyListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by alexc on 24/12/2016.
 */
public class GuiCMDListener extends MyListener {

    public GuiCMDListener() {
        super("GUICMDListener");
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equals("GUI View")) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.BARRIER) {
                e.getInventory().setItem(e.getSlot(), new ItemStack(Material.AIR));
            } else {
                new Button(e.getInventory(), ChatColor.GREEN + "Slot " + e.getSlot(), new String[]{}, new ItemStack(Material.BARRIER, e.getSlot()),
                        e.getSlot()).show();
            }
        }
    }

}
