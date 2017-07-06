package mineward.core.listener.custom;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MinewardPlayerChatEvent extends Event {

    private Player p;
    private String msg;
    private List<Player> recipients;

    public MinewardPlayerChatEvent(Player p, String msg, List<Player> recipients) {
        this.p = p;
        this.msg = msg;
        this.recipients = recipients;
    }

    public Player getPlayer() {
        return p;
    }

    public String getMessage() {
        return msg;
    }

    public List<Player> getRecipients() {
        return recipients;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public void setRecipients(List<Player> players) {
        this.recipients = players;
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
