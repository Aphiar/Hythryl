package mineward.core.elo;

import mineward.core.common.Database;
import mineward.core.listener.MyListener;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by alexc on 01/08/2016.
 */
public class EloManager extends MyListener {

    public EloManager() {
        super("Elo");
        Database.runUpdateStatement("CREATE TABLE IF NOT EXISTS Elo (id INT PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(100), elo INT)");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        try {
            ResultSet rs = Database.getConnection().prepareStatement("SELECT * FROM Elo WHERE uuid='"+p.getUniqueId().toString()+"'")
                    .executeQuery();

            if (!rs.next()) {
                Database.runUpdateStatement("INSERT INTO Elo(uuid, elo) VALUES ('"+p.getUniqueId().toString()+"','"+"0"+"')");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public static void AffectElo(OfflinePlayer p1, OfflinePlayer p2, int kills1, int kills2) {
        int p1Elo = GetElo(p1);
        int p2Elo = GetElo(p2);

        if (kills1 > kills2) {
            p2Elo += 400;
            p1Elo -= 400;
        } else {
            p1Elo += 400;
            p2Elo -= 400;
        }


    }

    public static int GetElo(OfflinePlayer p) {
        try {
            ResultSet rs = Database.getConnection().prepareStatement("SELECT * FROM Elo WHERE uuid='"+p.getUniqueId().toString()+"'")
                    .executeQuery();

            return rs.getInt("elo");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
