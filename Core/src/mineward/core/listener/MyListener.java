package mineward.core.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MyListener implements Listener {

	protected JavaPlugin main;
	private String name;

	public String getName() {
		return name;
	}

	public MyListener(String name) {
		this.name = name;
	}

	public void registerMe() {
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	public MyListener setMain(JavaPlugin main) {
		this.main = main;
		return this;
	}

}
