package net.arcanemc.core.plugin.command;

import net.arcanemc.core.users.User;
import org.bukkit.command.CommandExecutor;

public interface Executor extends CommandExecutor{

	public void execute(User user, String[] args);

	public String getName();
}