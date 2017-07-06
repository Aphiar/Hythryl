package mineward.core.achievement;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class CountAchievement extends Achievement {

    private int MaxAmount;

    public CountAchievement(String Name, String Desc, int Reward, int MaxAmount) {
        super(Name, Desc, Reward);
        this.MaxAmount = MaxAmount;
    }

    public void Complete(Player p, boolean msg, int add) {
        if (AchievementManager.hasAchievement(p, this)) {
            System.out.println(p.getName() + " has the achievement " + Name);
            return;
        }
        int data = AchievementManager.getAchievementData(p, this);
        if (add >= MaxAmount) {
            AchievementManager.removeAchievementData(p, this);
            this.Complete(p, msg);
            return;
        }
        if (data == -1) {
            AchievementManager.addAchievementData(p, this, add);
            if (msg) {
                p.sendMessage(" ");
                p.sendMessage(ChatColor.GREEN + " Achievement Data: "
                        + ChatColor.DARK_GRAY + this.Name + ChatColor.YELLOW
                        + " +" + add + ChatColor.GRAY + " - " + ChatColor.AQUA
                        + (MaxAmount - add) + " to complete");
                p.sendMessage(ChatColor.WHITE + " " + this.Description);
            }
            return;
        }
        if ((add + data) >= MaxAmount) {
            AchievementManager.removeAchievementData(p, this);
            this.Complete(p, msg);
            return;
        }
        AchievementManager.addAchievementData(p, this, add);
        if (msg) {
            p.sendMessage(" ");
            p.sendMessage(ChatColor.GREEN + " Achievement Data: "
                    + ChatColor.DARK_GRAY + this.Name + ChatColor.YELLOW + " +"
                    + add + ChatColor.GRAY + " - " + ChatColor.AQUA
                    + (MaxAmount - (add + data)) + " to complete");
            p.sendMessage(ChatColor.WHITE + " " + this.Description);
        }
    }

    public int getMaxAmount() {
        return MaxAmount;
    }

}
