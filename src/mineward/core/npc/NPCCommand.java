package mineward.core.npc;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.F;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class NPCCommand extends MyCommand {

	public NPCCommand() {
		super("npc", new String[0], "Summon and Vegetate an Entity", Rank.Admin);
	}

	@Override
	public void execute(Player p, String[] args) {
		if (args.length < 2) {
			F.help(p, "npc <type> <name>", "Summon entity and vegetate it",
					Rank.Admin);
			return;
		}
		String msg = "";
		for (int i = 1; i < (args.length - 1); i++) {
			msg += args[i] + " ";
		}
		msg += args[args.length - 1];
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		try {
			EntityType.valueOf(args[0]);
		} catch (Exception e) {
			p.sendMessage(ChatColor.RED + args[0]
					+ " is not an EntityType! Possible values: ");
			String m = "";
			for (int i = 0; i < EntityType.values().length - 1; i++) {
				m += EntityType.values()[i] + ", ";
			}
			m += EntityType.values()[EntityType.values().length - 1];
			p.sendMessage(ChatColor.GOLD + m);
			return;
		}
		EntityType type = EntityType.valueOf(args[0]);
		Entity e = p.getWorld().spawnEntity(p.getLocation(), type);
		if (!(e instanceof LivingEntity)) {
			e.remove();
			p.sendMessage(ChatColor.RED
					+ "This is not a living entity, therefore I cannot spawn it!");
			return;
		}
		LivingEntity le = (LivingEntity) e;
		le.setCustomNameVisible(true);
		le.setCanPickupItems(false);
		le.setMaxHealth(3000);
		le.setFireTicks(0);
		le.setRemoveWhenFarAway(false);
		le.setCustomName(msg);
		NPCManager.Vegetate(le);
	}

}
