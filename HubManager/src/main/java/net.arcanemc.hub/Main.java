package net.arcanemc.hub;

import net.arcanemc.core.plugin.PluginBase;
import net.arcanemc.hub.features.IED;
import net.arcanemc.hub.listeners.Damage;
import net.arcanemc.hub.listeners.Jump;
import net.arcanemc.hub.listeners.PlayerLocation;
import net.arcanemc.hub.listeners.RootPlayer;
import net.arcanemc.hub.particles.StormCloud;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class Main extends PluginBase {
    @Override
    public void onStart(){
        Jump=new Jump(this);
        SC=new StormCloud(this);
        PL=new PlayerLocation(this);
        IED=new IED(this);
        new RootPlayer(this);
        Bukkit.getServer().getPluginManager().registerEvents(new Damage(),this);
        PL.init();
        IED.aSyncIEDIndicator();
    }
    @Override
    public void onStop(){}
    // Classes
    Jump Jump;
    StormCloud SC;
    PlayerLocation PL;
    IED IED;

    // Plugin
    public String prefix="&5&o[&9&oHub&5&o] &7&o";

    // Local

    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender!=null&&sender instanceof Player){
            Player tgt=(Player)sender;
            if(lbl.equalsIgnoreCase("cloud")){
                Player hit=Bukkit.getPlayer(args[0]);
                SC.initCloud(hit);
            }else if(lbl.equalsIgnoreCase("ied")){
                IED.setIED(tgt);
            }
        }
        return true;
    }
    public StormCloud getSC(){return SC;}
    public IED getIED(){return IED;}
}
/*
* Jump *DONE*
*
* In Progress:
* Particles
* Getting ideas
* */