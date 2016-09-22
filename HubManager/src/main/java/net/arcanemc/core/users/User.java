package net.arcanemc.core.users;

import net.arcanemc.core.Core;
import net.arcanemc.core.inventory.Menu;
import net.arcanemc.core.permissions.Rank;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;

public class User {

	private UUID id;
	private Rank rank;
	private int coins;
	private int level;
	private int exp;

	private PermissionAttachment attachment;

	public User(UUID id) {
		this.id = id;
		this.attachment = getPlayer().addAttachment(Core.getPlugin(Core.class));
	}

	public Rank getRank() {
		return rank;
	}

	public UUID getUniqueId() {
		return id;
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(id);
	}

	public int getExp() {
		return exp;
	}

	public void addExp(int amount) {
		this.exp += amount;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getLevel() {
		return level;
	}

	public void addLevel() {
		this.level += 1;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getCoins() {
		return coins;
	}

	public void addCoins(int amount) {
		this.coins += amount;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	// Nifty Methods

	public void openMenu(Menu menu) {
		getPlayer().openInventory(menu.getInventory());
	}

	public void sendMessage(BaseComponent... components) {
		getPlayer().spigot().sendMessage(components);
	}

	public void addPermission(Permission permission) {
		this.attachment.setPermission(permission, true);
	}

	public void removePermission(String string) {
		this.attachment.unsetPermission(string);
		this.attachment.setPermission(string, false);
	}
}