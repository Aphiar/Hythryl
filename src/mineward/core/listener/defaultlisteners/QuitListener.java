package mineward.core.listener.defaultlisteners;

import java.sql.Timestamp;

import mineward.core.Core;
import mineward.core.achievement.time.TimeOnline;
import mineward.core.common.Database;
import mineward.core.listener.MyListener;
import mineward.core.player.HPlayer;
import mineward.core.scoreboard.SBManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener extends MyListener {

	public QuitListener() {
		super("DefaultQuit");
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		HPlayer player = HPlayer.o(p);
		TimeOnline.uploadToDatabase(p);
		TimeOnline.setOffline(p);

		e.setQuitMessage(null);

		long millis = System.currentTimeMillis();

		Database.runUpdateStatement("UPDATE `Account` SET `lastseen`='"
				+ millis + "' WHERE `uuid`='" + p.getUniqueId().toString()
				+ "';");

		System.out.println(new Timestamp(millis).toString());

		Core.RemovePlayer(player);

		SBManager.removeBoard(p);
	}

}
