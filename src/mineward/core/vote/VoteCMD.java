package mineward.core.vote;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class VoteCMD extends MyCommand {

    public VoteCMD() {
        super("vote", new String[] { "votesites", "voting"}, "Vote for the Hythryl Network", Rank.Default);
    }

    public void execute(Player p, String[] args) {
        String prefix = "&7[&5Vote&7]";
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &3----- &6Voting &3-----"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &eYou can vote at these sites:"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cSites coming soon!"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &eYou will receive 1000 coins and 500 experience for voting!"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &3----- &6Voting &3-----"));
    }
}