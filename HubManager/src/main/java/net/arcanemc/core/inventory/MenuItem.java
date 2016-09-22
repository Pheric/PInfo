package net.arcanemc.core.inventory;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public abstract class MenuItem {

	private ItemStack itemStack;
	
	public abstract void onClick(Player player);
	public void onRightClick(Player player) {}
	public void onShiftClick(Player player) {}
	
	public static ItemStack getNewStack(Material material, String name, List<String> lore, int amount, short dura) {
		ItemStack itemStack = new ItemStack(material);
		ItemMeta meta = itemStack.getItemMeta();
		if (itemStack.getType() == Material.SKULL_ITEM) {
			meta = (SkullMeta) itemStack.getItemMeta();
			((SkullMeta) meta).setOwner(ChatColor.stripColor(name));
		}
		meta.setDisplayName(name);
		itemStack.setDurability(dura);
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.values());
		itemStack.setItemMeta(meta);
		return itemStack;
	}

	public MenuItem(Material material, String name, List<String> lore) {
		this(new ItemStack(material), name, lore, 1, (short) 0);
	}

	public MenuItem(Material material, String name, List<String> lore, int amount) {
		this(new ItemStack(material), name, lore, amount, (short) 0);
	}
	
	public MenuItem(ItemStack stack, String name, List<String> lore, int amount, short dura) {
		ItemStack itemStack = stack;
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName(name);
		itemStack.setDurability(dura);
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.values());
		itemStack.setItemMeta(meta);
		this.itemStack = itemStack;
	}
	
	public ItemStack getItemStack() {
		return itemStack;
	}
	
	public MenuItem setSkull(OfflinePlayer player) {
		if (itemStack.getType() != Material.SKULL_ITEM) return null;
		SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
		if (!meta.setOwner(player.getName())) return null;
		if (player.isOnline()) {
			itemStack.setDurability((short) 3);
			List<String> lore = meta.getLore();
			lore.add(" ");
			lore.add(ChatColor.GREEN.toString() + "ONLINE");
			meta.setLore(lore);
		} else {
			itemStack.setDurability((short) 0);
			List<String> lore = meta.getLore();
			lore.add(" ");
			lore.add(ChatColor.RED.toString() + "OFFLINE");
			meta.setLore(lore);
		}
		itemStack.setItemMeta(meta);
		return this;
	}
}