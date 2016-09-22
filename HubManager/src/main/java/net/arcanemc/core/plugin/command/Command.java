package net.arcanemc.core.plugin.command;

import net.arcanemc.core.listeners.Users;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Command implements Executor {
	
	private String name;

	public Command(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean onCommand(CommandSender cs, org.bukkit.command.Command cmd, String label, String[] args) {
		execute(Users.manager.getUser(((Player)cs).getUniqueId()), args);
		return true;
	}
}