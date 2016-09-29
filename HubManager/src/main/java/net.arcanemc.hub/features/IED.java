package net.arcanemc.hub.features;

import net.arcanemc.hub.Main;
import net.arcanemc.hub.api.SelBlocks;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IED {
    private net.arcanemc.hub.Main Main;
    public IED(Main Main){
        this.Main=Main;
    }
    Map<Location,List<Location>> locations=new HashMap<>();
    public void setIED(Player tgt){
        Location loc=tgt.getLocation();
        tgt.getWorld().playSound(loc, Sound.CLICK,10,1);
        setMetadata(tgt,tgt.getLocation(),true);
        msg(tgt,Main.prefix+"IED set!");
    }
    public void detonate(Location loc){
        locations.remove(getContainingSet(loc).getKey());
        setMetadata(null,loc,false);

        loc.getWorld().playEffect(loc,Effect.EXPLOSION_HUGE,100);
    }
    public boolean isInSet(Location loc){
        for(Map.Entry<Location,List<Location>> set:locations.entrySet()){
            if(set.getValue().contains(loc)){
                return true;
            }
        }
        System.out.println(locations.size());
        return false;
    }
    public Map.Entry<Location, List<Location>> getContainingSet(Location loc){
        for(Map.Entry<Location,List<Location>> set:locations.entrySet()){
            if(set.getValue().contains(loc)){
                return set;
            }
        }
        return null;
    }
    private void setMetadata(Player tgt,Location loc,boolean mode){ // Mode==true: writes normally. mode==false: overwrites with NIU
        Location l1,l2;
        l1=new Location(loc.getWorld(),loc.getBlockX()+2,loc.getBlockY(),loc.getBlockZ()+2);
        l2=new Location(loc.getWorld(),loc.getBlockX()-2,loc.getBlockY(),loc.getBlockZ()-2);
        List<Location> locList=new ArrayList<>();
        if(mode){
            for(Block block: SelBlocks.selBlocksCuboid(l1,l2)){
                block.setMetadata("IED",new FixedMetadataValue(Main,tgt.getName()));
                locList.add(block.getLocation());
            }
            loc.getWorld().getBlockAt(loc.add(0,-1,0)).setType(Material.SNOW_BLOCK); // TODO: Temporary
            locations.put(loc,locList);
        }else{
            for(Block block: SelBlocks.selBlocksCuboid(l1,l2)){
                block.setMetadata("IED",new FixedMetadataValue(Main,"NIU"));
                locList.add(block.getLocation());
            }
        }
    }
    public void aSyncIEDIndicator(){
        new BukkitRunnable(){
            @Override
            public void run() {
                for(Location loc:locations.keySet()){
                    loc.getWorld().playEffect(loc, Effect.SMOKE,1); // Bug, spawns smoke IN the block. Do I even want to fix it?
                }
            }
        }.runTaskTimerAsynchronously(Main,0,5);
    }
    private void msg(Player target,String...message){
        for(String line:message){
            target.sendMessage(ChatColor.translateAlternateColorCodes('&',line));
        }
    }
}
