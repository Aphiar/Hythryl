package mineward.core.cooldown;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CooldownFinishEvent extends Event {

    private String cfor;
    private String data;
    private long time;

    public CooldownFinishEvent(String cfor, String data, long time) {
        this.cfor = cfor;
        this.data = data;
        this.time = time;
    }

    public String getFor() {
        return cfor;
    }

    public String getData() {
        return data;
    }

    public long getTime() {
        return time;
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
