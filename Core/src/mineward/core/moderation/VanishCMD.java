package mineward.core.moderation;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.UtilVanish;
import org.bukkit.entity.Player;

public class VanishCMD extends MyCommand {

    public VanishCMD() {
        super("vanish", new String[] { "v" }, "Vanish from other players!", Rank.Mod);
    }

    public void execute(Player p, String[] args) {
        if (UtilVanish.getVanished(p)) {
            UtilVanish.setUnVanished(p);
        } else {
            UtilVanish.setVanished(p);
        }
    }
}