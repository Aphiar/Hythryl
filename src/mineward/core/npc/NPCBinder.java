package mineward.core.npc;

import java.util.HashMap;
import java.util.UUID;

import mineward.core.listener.MyListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class NPCBinder extends MyListener {

	public static HashMap<UUID, String> ToBind = new HashMap<UUID, String>();

	public NPCBinder() {
		super("NPC Binder");
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (ToBind.containsKey(p.getUniqueId()))
			ToBind.remove(p.getUniqueId());
	}

	@EventHandler
	public void onEntityInteractEntity(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		Entity ent = e.getRightClicked();
		if (ToBind.containsKey(p.getUniqueId())) {
			String cmd = ToBind.get(p.getUniqueId());
			ArmorStand as = ent.getWorld().spawn(ent.getLocation(),
					ArmorStand.class);
			as.setCustomNameVisible(false);
			as.setCustomName(ent.getEntityId() + "§;" + cmd);
			as.setGravity(false);
			as.setArms(false);
			as.setBasePlate(false);
			as.setVisible(false);
			as.setSmall(true);
			as.setRemoveWhenFarAway(false);
			if (ent.getPassenger() == null) {
				as.teleport(ent.getLocation());
			} else {
				Entity tallest = ent.getPassenger();
				while (tallest.getPassenger() != null) {
					tallest = tallest.getPassenger();
				}
				tallest.setPassenger(as);
			}
			p.sendMessage(ChatColor.GREEN + "Successfully bound command " + cmd
					+ " to entity.");
			ToBind.remove(p.getUniqueId());
			e.setCancelled(true);
			return;
		}
		String cmd = null;
		if (ent.getPassenger() == null) {
			for (Entity ne : ent.getNearbyEntities(1, 1, 1)) {
				if (!(ne.getCustomName().contains("§;")))
					continue;
				if (ne.getLocation().getX() == ent.getLocation().getX()
						&& ne.getLocation().getY() == ent.getLocation().getY()
						&& ne.getLocation().getZ() == ent.getLocation().getZ()
						&& ne instanceof ArmorStand
						&& Integer.valueOf(ne.getCustomName().split("§;")[0])
								.intValue() == ent.getEntityId()) {
					cmd = ne.getCustomName();
					break;
				}
			}
			if (cmd == null) {
				return;
			}
		}
		if (cmd == null) {
			Entity tallest = ent.getPassenger();
			while (tallest.getPassenger() != null) {
				tallest = tallest.getPassenger();
			}
			cmd = tallest.getCustomName();
		}
		String command = cmd.split("§;")[1].replace("{p}", p.getName());
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
		e.setCancelled(true);
	}

}
