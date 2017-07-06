package mineward.core.hologram;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

public class Hologram {

    public String message;
    public Location loc;
    public int id;
    public ArmorStand as;

    public Hologram(String msg, Location loc) {
        this.message = msg;
        this.loc = loc;
    }

    public boolean build() {
        try {
            ArmorStand as = loc.getWorld().spawn(loc, ArmorStand.class);
            as.setGravity(false);
            as.setSmall(true);
            as.setVisible(false);
            as.setCustomNameVisible(true);
            as.setCustomName(ChatColor.translateAlternateColorCodes('&',
                    message));
            this.as = as;
            this.id = as.getEntityId();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void remove() {
        as.teleport(new Location(loc.getWorld(), loc.getX(), -1000, loc.getZ()));
        as.remove();
    }

}
