package mineward.core.premium;

import mineward.core.command.MyCommand;
import mineward.core.common.Database;
import mineward.core.common.Rank;
import mineward.core.common.utils.F;
import mineward.core.player.HPlayer;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by alexc on 11/03/2017.
 */
public class ReserveCMD extends MyCommand {

    public ReserveCMD() {
        super("reserve", new String[]{"becomehost"}, "Be the host of the premium server", Rank.Default);
    }

    @Override
    public void execute(Player p, String[] args) {

        try {
            ResultSet rs = Database.getConnection().prepareStatement("SELECT * FROM HostTokens WHERE uuid='"+p.getUniqueId()+"'").executeQuery();

            if (rs.next()) {

                int tokens = rs.getInt("tokens");

                if (tokens > 0) {

                    Rank r = HPlayer.o(p).getRank();

                    if (r == Rank.Default) {
                        PremiumManager.minutesLeft += 30;
                    } else if (r == Rank.Pro) {
                        PremiumManager.minutesLeft += 45;
                    } else if (r == Rank.Champion) {
                        PremiumManager.minutesLeft += 60;
                    } else if (r == Rank.Champion) {
                        PremiumManager.minutesLeft += 90;
                    }

                    PremiumManager.host = p;

                    Database.runUpdateStatement("UPDATE HostTokens SET tokens=tokens + 1 WHERE uuid='"+p.getUniqueId()+"'");

                    HPlayer.o(p).setRank(Rank.Manager);

                    F.message(p, "Premium Server", "You have redeemed one of your premium server tokens.");

                } else {
                    F.message(p, "Premium Server", "You do not have any premium server tokens.");
                }

            } else {

                F.message(p, "Premium Server", "You do not have any premium server tokens.");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
