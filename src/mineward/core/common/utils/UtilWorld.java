package mineward.core.common.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Created by alexcolville on 25/07/2016.
 */
public class UtilWorld {

    public static String locToStr(Location loc) {
        return loc.getWorld().getName() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ();
    }

    public static Location strToLoc(String loc) {
        String parts[] = loc.split(";");
        return new Location(
                Bukkit.getWorld(parts[0]),
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3])
        );
    }

}
