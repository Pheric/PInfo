package net.arcanemc.hub.particles;

import net.arcanemc.hub.Main;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class StormCloud {
    private Main Main;
    public StormCloud(Main Main){
        this.Main=Main;
        frozen=new ArrayList<>(); // Diamond operator; may cause issues with lower java versions
    }
    List<Player>frozen;
    public void initCloud(Player tgt){
        Location loc=tgt.getLocation();
        rootPlayer(tgt,loc);
        new BukkitRunnable(){
            @Override
            public void run() {
                for(int i=0;i<5;i++){
                    tgt.getWorld().strikeLightning(tgt.getLocation());
                }
            }
        }.runTaskLater(Main,50);
        for(int i=0;i<=8;i++){
            new BukkitRunnable(){
                @Override
                public void run() { //TODO: WIP
                    for(Player player: Bukkit.getServer().getOnlinePlayers()){
                        PacketPlayOutWorldParticles p = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_HUGE,true,(float)loc.getX(),(float)loc.getY()+15,(float)loc.getZ(),(float)5,(float)1,(float)5,1,100,null);
                        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(p);
                        try {
                            Thread.sleep(22);
                        } catch (InterruptedException e) {}
                        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(p);
                    }
                }
            }.runTaskLaterAsynchronously(Main,i*10);
        }
    }
    private void rootPlayer(Player tgt, Location loc){
        frozen.add(tgt);
        new BukkitRunnable(){
            @Override
            public void run() {
                frozen.remove(tgt); //TODO: Clear list in onStop()
            }
        }.runTaskLater(Main,60);
    }
    public boolean isFrozen(Player tgt){
        return frozen.contains(tgt);
    }
}
