package mineward.core.siri;

import java.util.Random;

import mineward.core.common.utils.F;

import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.entity.Player;

public class HythrylBot {

	public static void Siri(Player p, String msg) {
		p.playNote(p.getLocation(), Instrument.PIANO, new Note(1, Tone.G, true));
		if (msg.contains("apply") && msg.contains("staff")) {
			F.message(p, "Siri", "You can apply for staff at "
					+ ChatColor.GREEN + "" + ChatColor.UNDERLINE
					+ "www.hythryl.com/jrmod");
			return;
		}
		if (msg.contains("website") || msg.contains("webpage")) {
			F.message(p, "Siri", "My website is " + ChatColor.GREEN + ""
					+ ChatColor.UNDERLINE + "www.hythryl.com");
			return;
		}
		F.message(p, "Siri", randomMessage());
	}

	private static String randomMessage() {
		String[] msgs = new String[] {
				"Peanut Butter",
				"No, I do not like jelly beans",
				"I am a man",
				"That's none of your business",
				"Just because I am younger than you, doesn't mean you can bully me!",
				"I am not a woman", "Do you like chocolate", "No", "Yes",
				"Please", " I'd love some.", "Thank you", "You're ugly",
				"You're beautiful", "Do I look good with this dress on?",
				"Are you a boy or girl?" };
		int rand = new Random().nextInt(msgs.length);
		return msgs[rand];
	}

}
