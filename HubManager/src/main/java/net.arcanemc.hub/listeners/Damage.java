package net.arcanemc.hub.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class Damage implements Listener{
    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(event.getCause()==EntityDamageEvent.DamageCause.FALL){
            event.setCancelled(true);
        }
    }
}
