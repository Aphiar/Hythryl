package mineward.core.punish;

import org.bukkit.OfflinePlayer;

public class Punishment {

	public PunishType type;
	public long time;
	public long timepunished;
	public String reason;
	public String punisher;
	public OfflinePlayer punished;
	public int inactive = 0;
	public PunishCategory category;

	public Punishment(PunishType type, long time, String reason,
			String punisher, OfflinePlayer punished, long timepunished,
			PunishCategory category) {
		this.type = type;
		this.time = time;
		this.reason = reason;
		this.punisher = punisher;
		this.punished = punished;
		this.timepunished = timepunished;
		this.category = category;
	}

}
