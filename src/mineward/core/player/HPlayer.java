package mineward.core.player;

import java.util.HashMap;

import mineward.core.Core;
import mineward.core.common.Rank;
import mineward.core.common.database.account.AccountManager;
import mineward.core.common.database.account.AccountManager.Account;

import org.bukkit.entity.Player;

public class HPlayer {

    private Player p;
    private Rank rank;
    private int coins;
    private Account acc;
    private long xp;
    private HashMap<String, Integer> money;

    public static HPlayer a(Player p) {
        HPlayer player = new HPlayer();
        player.p = p;
        Account acc = AccountManager.getAccount(p.getUniqueId().toString(),
                false);
        player.rank = acc.rank;
        player.coins = acc.money;
        player.acc = acc;
        player.xp = acc.xp;
        player.money = acc.moneyTypes;
        return player;
    }

    public static HPlayer o(Player p) {
        for (HPlayer pl : Core.getOnlinePlayers()) {
            // Bukkit.broadcastMessage(pl.getPlayer().getName() + " - "
            // + pl.getPlayer().getUniqueId().toString() + ", "
            // + p.getName() + " - " + p.getUniqueId().toString());
            if (pl.getPlayer().getUniqueId().equals(p.getUniqueId())) {
                return pl;
            }
        }
        return null;
    }

    public Player getPlayer() {
        return p;
    }

    public Rank getRank() {
        return rank;
    }

    public int getMoney() {
        return coins;
    }

    public Account getAccount() {
        return acc;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setMoney(int a) {
        this.coins = a;
    }

    public long getXP() {
        return xp;
    }

    public void setXP(long a) {
        this.xp = a;
    }

    public void setMoney(String type, int a) {
        money.put(type, a);
    }

    public int getMoney(String type) {
        return money.get(type);
    }

}
