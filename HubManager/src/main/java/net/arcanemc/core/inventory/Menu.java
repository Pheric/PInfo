package net.arcanemc.core.inventory;

import net.arcanemc.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Menu implements Listener {

	Map<Integer, MenuItem> items = new HashMap<Integer, MenuItem>();
	Inventory inv;
	boolean close = false;

	public Menu(String name, int rows, Player player) {
		this(name, rows, player, false);
	}

	public Menu(String name, int rows, Player player, boolean close) {
		inv = Bukkit.createInventory(player, rows * 9, name);
		Bukkit.getPluginManager().registerEvents(this, Core.getPlugin(Core.class));
		this.close = close;
	}

	public void build(boolean clear) {
		if (clear)
			inv.clear();
		for (Entry<Integer, MenuItem> entries : items.entrySet()) {
			inv.setItem(entries.getKey(), entries.getValue().getItemStack());
		}
	}

	public void build() {
		build(false);
	}

	public Inventory getInventory() {
		return inv;
	}

	public void setItem(int slot, MenuItem menuItem) {
		items.put(slot, menuItem);
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getCurrentItem() == null) return;
		if (!event.getClickedInventory().equals(inv)) return;
		try {
			if (event.getAction() == InventoryAction.PICKUP_ALL) {
				items.get(event.getSlot()).onClick((Player) event.getWhoClicked());
				event.setCancelled(true);
			} else if (event.getAction() == InventoryAction.PICKUP_HALF) {
				items.get(event.getSlot()).onRightClick((Player) event.getWhoClicked());
				event.setCancelled(true);
			} else if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
				items.get(event.getSlot()).onShiftClick((Player) event.getWhoClicked());
				event.setCancelled(true);
			}
			if (close) event.getWhoClicked().closeInventory();
			event.setCancelled(true);
			return;
		} catch (Exception ex) {
			event.setCancelled(true);
			((Player) event.getWhoClicked()).sendMessage(ChatColor.RED + "An error was thrown while attempting to handle your click!");
			((Player) event.getWhoClicked()).sendMessage(ChatColor.RED + ex.getMessage());
		}
	}
}