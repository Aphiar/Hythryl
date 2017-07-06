package mineward.core.common.utils.gui;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.F;
import mineward.core.gui.Button;
import mineward.core.gui.GUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by alexc on 24/12/2016.
 */
public class GuiCMD extends MyCommand {

    public GuiCMD() {
        super("gui", new String[]{}, "Open a gui view", Rank.Dev);
    }

    @Override
    public void execute(Player p, String[] args) {
        if (args.length == 0) {
            F.message(p, "Usage", "/gui <Rows> [e]");
            return;
        }

        GUI gui = GUI.createGUI("GUI View", Integer.parseInt(args[0]) * 9);

        if (args.length < 2) {
            for (int i = 0; i < Integer.parseInt(args[0]) * 9; i++) {
                gui.AddButton(new Button(gui.inv, ChatColor.GREEN + "Slot " + i, new String[]{}, new ItemStack(Material.BARRIER, i), i));
            }
        }

        gui.show(p);
    }

}
