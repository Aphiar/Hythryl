package mineward.core.release;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.F;
import org.bukkit.entity.Player;

/**
 * Created by alexc on 31/07/2016.
 */
public class ReleaseCMD extends MyCommand {

    public ReleaseCMD() {
        super("release", new String[]{}, "", Rank.Owner);
    }

    public void execute(Player p, String args[]) {
        if (args.length == 0) {
            F.help(p, "release <Minutes>", "Start the release party", Rank.Owner);
        } else if (args[0].equalsIgnoreCase("broadcast")) {
            String s = "";

            for (int i = 0; i < args.length; i++) {
                s += args[i] + " ";
            }

            ReleaseParty.CurrentParty.broadcast(s);
        } else {
            new ReleaseParty(p, Double.parseDouble(args[0]));
        }
    }

}
