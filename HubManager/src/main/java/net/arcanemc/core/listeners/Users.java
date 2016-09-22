package net.arcanemc.core.listeners;

import net.arcanemc.core.permissions.Rank;
import net.arcanemc.core.users.UserManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class Users implements Listener {

	public static UserManager manager;

	public Users() {
		manager = new UserManager();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		net.arcanemc.core.users.User user = manager.loadUser(event.getPlayer().getUniqueId());
		for (PermissionAttachmentInfo info : user.getPlayer().getEffectivePermissions()) {
			user.removePermission(info.getPermission());
		}
	}

	@EventHandler
	public void onTab(PlayerChatTabCompleteEvent e) {
		e.getTabCompletions().clear();
		e.getChatMessage().charAt(0);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		if (manager.getUser(event.getPlayer().getUniqueId()) == null)
			return;
		manager.saveUser(manager.getUser(event.getPlayer().getUniqueId()));
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		net.arcanemc.core.users.User player = manager.getUser(event.getPlayer().getUniqueId());
		if (player.getRank() != Rank.MEMBER) {
			BaseComponent component = new TextComponent(ChatColor.GRAY.toString() + player.getLevel() + " "
					+ ChatColor.YELLOW + player.getPlayer().getName() + ChatColor.DARK_GRAY + " \u00BB "
					+ ChatColor.WHITE + event.getMessage());
			component.setClickEvent(
					new ClickEvent(Action.RUN_COMMAND, "/stats " + event.getPlayer().getName()));
			player.getPlayer().spigot().sendMessage(component);
		} else {
			BaseComponent component = new TextComponent(ChatColor.GRAY.toString() + player.getLevel() + " "
					+ player.getRank().getPrefix() + " " + ChatColor.YELLOW + player.getPlayer().getName()
					+ ChatColor.DARK_GRAY + " \u00BB " + ChatColor.WHITE + event.getMessage());
			component.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/stats " + event.getPlayer().getName()));
			player.getPlayer().spigot().sendMessage(component);
		}
	}
}