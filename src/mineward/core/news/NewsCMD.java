package mineward.core.news;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.F;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

/**
 * Created by alexc on 28/07/2016.
 */
public class NewsCMD extends MyCommand {

    public NewsCMD() {
        super("news", new String[]{}, "Modify the news", Rank.Owner);
    }

    @Override
    public void execute(Player p, String[] args) {
        if (args.length ==0) {
            F.help(p, "news <News>", "Set the news", Rank.Owner);
            return;
        }

        NewsManager.SetNews(StringUtils.join(args, " "));
        F.message(p, "News", "The news was updated!");
    }
}
