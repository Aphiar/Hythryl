package mineward.core.chat;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.UtilFilter;
import org.bukkit.entity.Player;

/**
 * Created by alexcolville on 24/07/2016.
 */
public class FilterCMD extends MyCommand {

    public FilterCMD() {
        super("filter", new String[]{}, "Add a word to the filter.", Rank.Srmod);
    }

    @Override
    public void execute(Player p, String[] args) {
        String s = "";

        for (int i = 0; i < args.length; i++) {
            s += " " + args[i];
        }

        UtilFilter.addWord(p, s.substring(1));
    }
}
