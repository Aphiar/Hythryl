package mineward.core.shop;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GamePurchaseEvent extends Event {

	private String item;
	private String dpNm;
	private int cost;
	private Player p;

	public GamePurchaseEvent(Player p, String item, String dpNm, int cost) {
		this.item = item;
		this.dpNm = dpNm;
		this.cost = cost;
		this.p = p;
	}

	public Player getPlayer() {
		return p;
	}

	public String getItem() {
		return item;
	}

	public String getDisplayName() {
		return dpNm;
	}

	public int getCost() {
		return cost;
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
