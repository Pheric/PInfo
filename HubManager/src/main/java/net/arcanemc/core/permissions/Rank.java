package net.arcanemc.core.permissions;

import org.bukkit.ChatColor;

public enum Rank {
	
	OWNER(11, "Owner", "&8&l[&4&lOWNER&8&l]"),
	ADMIN(10, "Admin", "&8&l[&6&lADMIN&8&l]"), 
	DEV(9, "Developer", "&8&l[&6&lDEV&8&l]"), 
	SMOD(8, "S.MOD", "&8&l[&c&lS.MOD&8&l]"),
	MOD(7, "Moderator", "&8&l[&c&lMOD&8&l]"), 
	HELPER(6, "Helper", "&8&l[&2&lHELPER&8&l]"),
	CONTENT(5, "Content", "&8&l[&9&lCONTENT&8&l]"),
	BUILDER(4, "Builder", "&8&l[&9&lBUILDER&8&l]"),
	YOUTUBER(3, "YouTuber", "&8&l[&f&lYou&c&lTuber&8&l]"),
	ELITE(2, "Elite", "&b[ELITE]"),
	MEMBER(1, "Member", "");
	
	private int id;
	private String name;
	private String prefix;

	private Rank(int id, String name, String prefix) {
		this.id = id;
		this.name = name;
		this.prefix = prefix;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', prefix);
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	public static Rank valueOf(int i) {
		for (Rank ranks : Rank.values()) {
			if (ranks.getId() == i) {
				return ranks;
			}
		}
		return null;
	}
}