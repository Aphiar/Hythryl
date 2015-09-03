package mineward.core.fun;

import mineward.core.command.Muteable;
import mineward.core.command.MyCommand;
import mineward.core.common.Rank;

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.entity.Player;

public class FartCMD extends MyCommand implements Muteable {

	public FartCMD() {
		super("fart", new String[] {}, "Just 4 funzies", Rank.Default);
	}

	@Override
	public void execute(Player p, String[] args) {
		for (int i = 0; i < 3; i++) {
			for (int b = 0; b < (Tone.values().length); b++) {

				boolean caught = true;
				try {
					p.playNote(p.getLocation(), Instrument.BASS_DRUM, new Note(
							i, Tone.values()[b], false));
				} catch (Exception e) {
					caught = false;
				}
				if (caught) {
					try {
						Thread.sleep(8);
					} catch (Exception e) {
						// e.printStackTrace();
					}
				}
			}
		}
	}

}
