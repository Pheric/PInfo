package net.arcanemc.core;

import net.arcanemc.core.backend.SQLManager;
import net.arcanemc.core.commands.FriendsCommand;
import net.arcanemc.core.commands.StatsCommand;
import net.arcanemc.core.listeners.Users;
import net.arcanemc.core.plugin.PluginBase;
import net.arcanemc.core.users.UserManager;

public class Core extends PluginBase {
	
	private static SQLManager sql = new SQLManager("127.0.0.1", "root", "KjfM8yarbyKy", "arcanemc", 3306);
	private static UserManager users = new UserManager();

	@Override
	public void onStart() {
		registerListener(new Users());
		registerCommand(new StatsCommand());
		registerCommand(new FriendsCommand());
	}

	@Override
	public void onStop() {
		sql.close();
	}
	
	public static SQLManager getSQL() {
		return sql;
	}
	
	public static UserManager getUsers() {
		return users;
	}
}