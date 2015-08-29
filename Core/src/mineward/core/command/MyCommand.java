package mineward.core.command;

import java.util.Arrays;

import mineward.core.common.Prefix.PrefixColor;
import mineward.core.common.Rank;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;
import mineward.core.common.utils.TimeUtil;
import mineward.core.player.HPlayer;
import mineward.core.punish.Punish;
import mineward.core.punish.Punishment;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MyCommand extends BukkitCommand {

	private String[] aliases;
	private String label;
	private String aliasUsed;
	private Rank rank;
	private JavaPlugin plugin;

	public MyCommand(String label, String[] aliases, String description,
			Rank rank) {
		super(label);
		this.aliases = aliases;
		this.rank = rank;
		this.label = label;
		this.description = description;
		this.setAliases(Arrays.asList(aliases));
	}

	public String getLabel() {
		return this.label;
	}

	public String[] getCommandAliases() {
		return this.aliases;
	}

	public JavaPlugin getPlugin() {
		return this.plugin;
	}

	public Rank getRank() {
		return this.rank;
	}

	public String getAliasUsed() {
		return this.aliasUsed;
	}

	public void setAliasUsed(String alias) {
		this.aliasUsed = alias;
	}

	public MyCommand setMain(JavaPlugin main) {
		this.plugin = main;
		return this;
	}

	public abstract void execute(Player p, String[] args);

	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			Punishment pun = Punish.getActiveMutePunishment(p.getUniqueId());
			if (this instanceof Muteable) {
				if (pun != null) {
					String time = pun.time == -1 ? "Forever" : TimeUtil
							.toString((pun.time + pun.timepunished)
									- System.currentTimeMillis());
					F.message(p, PrefixColor.Important, C.STR_PLAYER
							+ pun.punisher + C.STR_MAIN
							+ " forbids you from using this command for "
							+ C.STR_ELEMENT + time + C.STR_MAIN
							+ " because you were " + C.STR_ELEMENT + pun.reason);
					return false;
				}
			}
			if (Rank.isPermissible(HPlayer.o(p), getRank(), true)) {
				this.execute(p, args);
			}
		}
		return false;
	}

	public void registerMe() {
		((CraftServer) plugin.getServer()).getCommandMap().register(
				"HythrylCommand", this);
		CommandCenter.CommandMap.add(this);
	}

}
