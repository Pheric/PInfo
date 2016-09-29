package net.arcanemc.hub.listeners;

import net.arcanemc.hub.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class RootPlayer implements Listener{
    private Main Main;
    public RootPlayer(Main Main){
        this.Main=Main;
        Bukkit.getServer().getPluginManager().registerEvents(this,Main);
    }
    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent event) { // Credit to Oberdiah for this code
        if ((event.getFrom().getX() != event.getTo().getX() || event.getFrom().getY() != event.getTo().getY() || event.getFrom().getZ() != event.getTo().getZ())&&Main.getSC().isFrozen(event.getPlayer())) {
            Location loc = event.getFrom();
            event.getPlayer().teleport(loc.setDirection(event.getTo().getDirection()));
        }
    }
}
