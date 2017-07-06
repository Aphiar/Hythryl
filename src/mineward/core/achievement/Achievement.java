package mineward.core.achievement;

import mineward.core.common.utils.UtilCoin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class Achievement {

    protected int MoneyReward;
    protected String Name;
    protected String Description;

    public void Complete(Player p, boolean msg) {
        if (AchievementManager.hasAchievement(p, this))
            return;
        UtilCoin.AddCoins(p, MoneyReward);
        AchievementManager.addAchievement(p, this);
        if (msg) {
            p.sendMessage(" ");
            p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD
                    + "ACHIEVEMENT GET!   " + ChatColor.GREEN + Name
                    + ChatColor.LIGHT_PURPLE + "   +" + MoneyReward + " Coins");
            p.sendMessage(ChatColor.GRAY + Description);
            p.sendMessage(" ");
        }
    }

    public Achievement(String Name, String Desc, int Reward) {
        this.Name = Name;
        this.Description = Desc;
        this.MoneyReward = Reward;
        for (Achievement a : AchievementManager.aelist) {
            if (a.Name.equals(this.Name)) {
                return;
            }
        }
        AchievementManager.aelist.add(this);
        System.out.println("Achievement > Added Achievement " + Name);
    }

    public int getReward() {
        return MoneyReward;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

}
