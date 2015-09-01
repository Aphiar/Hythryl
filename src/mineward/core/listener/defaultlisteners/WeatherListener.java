package mineward.core.listener.defaultlisteners;

import mineward.core.listener.MyListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener extends MyListener {

	public WeatherListener() {
		super("NoRain");
	}

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		e.setCancelled(true);
	}

}
