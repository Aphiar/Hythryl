package mineward.core.fun;

import java.util.UUID;

import mineward.core.listener.MyListener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TwerkEvents extends MyListener {

	public TwerkEvents() {
		super("TwerkEvents");
		Bukkit.getServer().getScheduler()
				.scheduleSyncRepeatingTask(main, new Runnable() {
					public void run() {
						for (UUID id : TwerkCMD.players) {
							Player p = Bukkit.getPlayer(id);
							p.setSneaking(!p.isSneaking());
						}
					}
				}, 2L, 0L);
	}

}
