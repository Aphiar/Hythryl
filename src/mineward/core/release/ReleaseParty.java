package mineward.core.release;

import mineward.core.Core;
import mineward.core.common.Prefix;
import mineward.core.common.PrefixBuilder;
import mineward.core.common.utils.F;
import mineward.core.common.utils.TimeUtil;
import mineward.core.common.utils.UtilCoin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

/**
 * Created by alexc on 31/07/2016.
 */
public class ReleaseParty implements Listener {

    public static ReleaseParty CurrentParty = null;

    private Prefix p;
    private Player host;
    private long seconds;
    private double minutes;

    public ReleaseParty(Player host, double minutesToRelease) {
        this.host = host;
        seconds = (int) minutesToRelease * 60;
        minutes = minutesToRelease;


        CurrentParty = this;
        Bukkit.getPluginManager().registerEvents(this, Core.getPlugin());

        p = PrefixBuilder.getPrefixBuilder(false, "Release Party", Prefix.PrefixColor.Normal).build();


        new BukkitRunnable() {
            @Override
            public void run() {
                if (seconds == 0) {
                    F.broadcastMessage(p, "§eHythryl §7has officially §a§lRELEASED§7!");
                    for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
                        UtilCoin.AddCoins(p, 20000);
                    }
                    F.broadcastMessage(p, "Everyone that has joined up to this point has been given 20,000 coins!");
                    cancel();
                } else {
                    seconds--;
                    minutes = (double) seconds / 60;

                    String text = Double.toString(Math.abs(minutes));
                    int integerPlaces = text.indexOf('.');
                    int decimalPlaces = text.length() - integerPlaces - 1;

                    if (decimalPlaces == 1)
                        F.broadcastMessage(p, "§e" + minutes + " Minutes §7until Hythryl releases!");
                }
            }
        }.runTaskTimer(Core.getPlugin(), 20L, 20L);
    }

    public void broadcast(String msg) {
        F.broadcastMessage(p, ChatColor.translateAlternateColorCodes('&', msg));
    }

}
