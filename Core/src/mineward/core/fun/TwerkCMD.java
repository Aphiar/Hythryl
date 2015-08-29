package mineward.core.fun;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mineward.core.command.MyCommand;
import mineward.core.common.Prefix.PrefixColor;
import mineward.core.common.Rank;
import mineward.core.common.utils.F;

import org.bukkit.entity.Player;

public class TwerkCMD extends MyCommand {

	public static List<UUID> players = new ArrayList<UUID>();

	public TwerkCMD() {
		super("twerk", new String[] {}, "Twerk a little", Rank.Pro);
	}

	@Override
	public void execute(Player p, String[] args) {
		if (players.contains(p.getUniqueId())) {
			players.remove(p.getUniqueId());
			F.message(p, PrefixColor.Easy, "You are no longer twerking.");
			return;
		}
		players.add(p.getUniqueId());
		F.message(p, PrefixColor.Easy,
				"You are now twerking! Although you cannot see it, your friends surely can!");
		F.message(p, PrefixColor.Normal, "Use /twerk again to toggle off.");
	}

}
