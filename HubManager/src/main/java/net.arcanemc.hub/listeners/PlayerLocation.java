package net.arcanemc.hub.listeners;

import net.arcanemc.hub.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerLocation {
    private Main Main;
    public PlayerLocation(Main Main){
        this.Main=Main;
    }
    public void init(){
        new BukkitRunnable(){
            @Override
            public void run() {
                Bukkit.getServer().getOnlinePlayers().forEach((tgt)->{
                    Location loc=tgt.getLocation();
                    Location roundedLoc=new Location(loc.getWorld(),loc.getBlockX(),loc.getBlockY(),loc.getBlockZ());
                    if(loc.getBlock()!=null&&loc.getBlock().hasMetadata("IED")){
                        if(!(loc.getBlock().getMetadata("IED").get(0).asString().equals("NIU"))&&!(tgt.getName().equals(loc.getBlock().getMetadata("IED").get(0).asString()))){
                            if(Main.getIED().isInSet(roundedLoc)){
                                Main.getIED().detonate(roundedLoc);
                            }
                        }
                    }
                });
            }
        }.runTaskTimerAsynchronously(Main,0,2);
    }
}
/* // TODO:
radius is untested
The ied explosion/detonation in general
 */