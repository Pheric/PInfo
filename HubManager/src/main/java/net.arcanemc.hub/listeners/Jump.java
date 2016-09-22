package net.arcanemc.hub.listeners;

import net.arcanemc.hub.Main;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Jump implements Listener{
    private boolean debug=false;
    private Main Main;
    public Jump(Main Main){
        this.Main=Main;
        Bukkit.getServer().getPluginManager().registerEvents(this,Main);
        debug("Made");
    }

    // Jumping option(s)
    double jumpMultiplier=1.5;
    double jumpY=.5;

    private Player tgt;
    @EventHandler
    public void onMove(PlayerMoveEvent event){
        debug("onMove");
        tgt=event.getPlayer();
        debug(tgt.isFlying());
        if(tgt.getGameMode()!=GameMode.CREATIVE&&tgt.isOnGround()&&!tgt.isFlying()){
            tgt.setAllowFlight(true);
            debug("Flight: true");
        }
    }
    @EventHandler
    public void flightToggle(PlayerToggleFlightEvent event){
        debug("flightToggle");
        tgt=event.getPlayer();
        if(tgt.getGameMode()==GameMode.CREATIVE)return;
        event.setCancelled(true);
        tgt.setAllowFlight(false);
        tgt.setFlying(false);
        tgt.setVelocity(tgt.getLocation().getDirection().multiply(jumpMultiplier).setY(jumpY));
        // Effects
        tgt.getWorld().playSound(tgt.getLocation(),Sound.GHAST_FIREBALL,10,1);
        particleTrail();

        debug("Shot");
    }
    private void particleTrail(){
        new BukkitRunnable(){
            @Override
            public void run() {
                if(tgt.isOnGround())this.cancel();
                PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                        EnumParticle.TOWN_AURA,                   // particle type.
                        true,                                        // true
                        (float)tgt.getLocation().getX(),             // x coordinate
                        (float)tgt.getLocation().getY(),             // y coordinate
                        (float)tgt.getLocation().getZ(),             // z coordinate
                        (float).15,                                    // x offset
                        (float).15,                                    // y offset
                        (float).15,                                    // z offset
                        3,                                          // speed
                        100,                                       // number of particles
                        null
                );
                ((CraftPlayer)tgt).getHandle().playerConnection.sendPacket(packet);
                }
            }.runTaskTimerAsynchronously(Main,0,1
        );
    }
    private void debug(String...array){
        if(!debug)return;
        for(String line:array){
            if (line.contains("$b")){
                for(Player target:Bukkit.getServer().getOnlinePlayers()){
                    String l=line.replace("$b","");
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&',l));
                    System.out.println(l);
                }
            }else{
                System.out.println(line);
            }
        }
    }
    private void debug(boolean a){
        debug(a+"");
    }
}
