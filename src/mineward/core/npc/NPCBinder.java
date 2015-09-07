package mineward.core.npc;

import java.util.HashMap;
import java.util.UUID;

import mineward.core.listener.MyListener;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
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
			net.minecraft.server.v1_8_R3.Entity en = ((CraftEntity) ent)
					.getHandle();
			NBTTagCompound tag = new NBTTagCompound();
			en.c(tag);
			tag.setString("CustomName", cmd);
			en.f(tag);
			p.sendMessage(ChatColor.GREEN + "Successfully bound command "
					+ tag.getString("CustomName") + " to entity.");
			ToBind.remove(p.getUniqueId());
			e.setCancelled(true);
			return;
		}
		net.minecraft.server.v1_8_R3.Entity en = ((CraftEntity) ent)
				.getHandle();
		NBTTagCompound tag = new NBTTagCompound();
		en.c(tag);
		String cmd = tag.getString("CustomName");
		if (cmd == null)
			return;
		String command = cmd.replace("{p}", p.getName());
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
		e.setCancelled(true);
	}

}