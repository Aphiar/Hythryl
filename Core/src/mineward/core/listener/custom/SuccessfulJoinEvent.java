package mineward.core.listener.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SuccessfulJoinEvent extends Event {

	private Player p;

	public SuccessfulJoinEvent(Player p) {
		this.p = p;
	}

	public Player getPlayer() {
		return p;
	}

	/**
	 * HandlerList stuff
	 */
	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
