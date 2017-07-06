package mineward.core.premium;

import mineward.core.Core;
import mineward.core.common.Database;
import mineward.core.common.utils.UtilFile;
import mineward.core.common.utils.UtilSys;
import mineward.core.listener.MyListener;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * Created by alexc on 11/03/2017.
 */
public class PremiumManager extends MyListener {

    public PremiumManager() {
        super("Premium Manager");

        if (new File(Core.class.getProtectionDomain().getCodeSource()
                .getLocation().getPath()).getParentFile().getParentFile()
                .getName().equals("Premium-1")) {

            premServer = true;

        }
    }

    public static boolean premServer = false;
    public static Player host;
    public static int minutesLeft = 0;



}
