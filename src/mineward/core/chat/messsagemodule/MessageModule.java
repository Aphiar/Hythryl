package mineward.core.chat.messsagemodule;

import java.util.HashMap;
import java.util.UUID;

import mineward.core.achievement.general.MsgAchievement;

import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.entity.Player;

public class MessageModule {

    public static HashMap<UUID, UUID> replies = new HashMap<UUID, UUID>();

    public static void message(Player p, Player target, String msg) {
        target.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD
                + p.getName() + " > You: " + ChatColor.DARK_PURPLE + msg);
        p.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "You > "
                + target.getName() + ": " + ChatColor.DARK_PURPLE + msg);
        p.playNote(p.getLocation(), Instrument.PIANO,
                new Note(1, Tone.F, false));
        target.playNote(target.getLocation(), Instrument.PIANO, new Note(1,
                Tone.F, false));
        replies.put(p.getUniqueId(), target.getUniqueId());
        replies.put(target.getUniqueId(), p.getUniqueId());
        if (p.getUniqueId().equals(target.getUniqueId()))
            return;
        new MsgAchievement().Complete(p, true);
    }

}
