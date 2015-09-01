package mineward.core.fun;

import mineward.core.command.Muteable;
import mineward.core.command.MyCommand;
import mineward.core.common.Rank;

import org.bukkit.entity.Player;

public class SkyRocket extends MyCommand implements Muteable {

	public SkyRocket() {
		super("vegas", new String[] {}, "Who doesn't love vegas?", Rank.Default);
	}

	@Override
	public void execute(Player p, String[] args) {

	}

}
