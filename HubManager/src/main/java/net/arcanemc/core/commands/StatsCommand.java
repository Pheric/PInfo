package net.arcanemc.core.commands;

import net.arcanemc.core.inventory.Menu;
import net.arcanemc.core.plugin.command.Command;
import net.arcanemc.core.users.User;
import org.bukkit.ChatColor;

public class StatsCommand extends Command {
	
	public StatsCommand() {
		super("stats");
	}

	@Override
	public void execute(User user, String[] args) {
		Menu menu = new Menu(ChatColor.RED + "Stats: " + user.getPlayer().getName(), 3, user.getPlayer());
		menu.build();
		user.openMenu(menu);
	}
}