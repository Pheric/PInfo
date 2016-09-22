package net.arcanemc.core.plugin;

import net.arcanemc.core.plugin.command.Executor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PluginBase extends JavaPlugin implements Plugin {

	@Override
	public void onEnable() {
		onStart();
	}
	
	@Override
	public void onDisable() {
		onStop();
	}
	
	public void registerListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}
	
	public void registerCommand(Executor exe) {
		getCommand(exe.getName()).setExecutor(exe);
	}
}