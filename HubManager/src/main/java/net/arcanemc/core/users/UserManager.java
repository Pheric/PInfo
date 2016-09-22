package net.arcanemc.core.users;

import net.arcanemc.core.Core;
import net.arcanemc.core.backend.SQLManager;
import net.arcanemc.core.permissions.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {

	private Map<UUID, User> users = new HashMap<>();

	public User loadUser(UUID uuid) {
		User user = new User(uuid);
		try {
			SQLManager sql = Core.getSQL();
			ResultSet set = sql.executeQuery("SELECT * FROM users WHERE uuid='" + user.getUniqueId().toString() + "'");
			if (set.next()) {
				user.setCoins(set.getInt("coins"));
				user.setLevel(set.getInt("level"));
				user.setRank(Rank.valueOf(set.getInt("rank")));
				user.setExp(set.getInt("exp"));
			} else {
				user.setCoins(0);
				user.setExp(0);
				user.setLevel(0);
				user.setRank(Rank.MEMBER);
			}
			users.put(uuid, user);
			return user;
		} catch (SQLException e) {
			Bukkit.getPlayer(uuid).kickPlayer(ChatColor.DARK_GRAY + "\u2716" + ChatColor.RED.toString() + ChatColor.BOLD
					+ " Error " + ChatColor.DARK_GRAY + "\u2716 \n\n" + ChatColor.RED
					+ "An error in the database has risen! Please contact a administrator with the following to resolve this problem!\n"
					+ e.getMessage() + "\n\n" + ChatColor.RED + "Please remember to take a screenshot of this screen!");
			System.out.println("Error while loading player: " + user.getPlayer().getName());
			return null;
		}
	}

	public void loadUsers() {
		for (Player user : Bukkit.getOnlinePlayers()) {
			loadUser(user.getUniqueId());
		}
	}

	public void saveUser(User user) {
		try {
			SQLManager sql = Core.getSQL();
			if (sql.executeQuery("SELECT * FROM users WHERE uuid='" + user.getUniqueId().toString() + "'").next()) {
				sql.executeUpdate("UPDATE users SET coins='" + user.getCoins() + "', level='" + user.getLevel()
						+ "', rank='" + user.getRank().getId() + "', exp='" + user.getExp() + "' WHERE uuid='"
						+ user.getUniqueId().toString() + "';");
			} else {
				sql.executeUpdate("INSERT INTO users(uuid, coins, level, rank, exp) VALUES('"
						+ user.getUniqueId().toString() + "', '" + user.getCoins() + "', '" + user.getLevel() + "', '"
						+ user.getRank().getId() + "', '" + user.getExp() + "')");
			}
		} catch (SQLException e) {
			System.out.println("Error while saving player: " + user.getPlayer().getName());
		}
	}

	public void saveUsers() {
		for (User user : getUsers()) {
			saveUser(user);
		}
	}

	public Collection<User> getUsers() {
		return users.values();
	}

	public User getUser(UUID uniqueId) {
		return users.get(uniqueId);
	}
}