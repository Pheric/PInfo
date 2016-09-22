package net.arcanemc.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class Server {

	@EventHandler
	public void onWeather(WeatherChangeEvent event) {
		event.getWorld().setThundering(false);
		event.getWorld().setStorm(false);
	}

	@EventHandler
	public void onPing(ServerListPingEvent event) {
		
	}
}