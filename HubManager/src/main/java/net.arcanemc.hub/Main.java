package net.arcanemc.hub;

import net.arcanemc.core.plugin.PluginBase;
import net.arcanemc.hub.listeners.Damage;
import net.arcanemc.hub.listeners.Jump;
import org.bukkit.Bukkit;

public final class Main extends PluginBase {
    @Override
    public void onStart(){
        Jump=new Jump(this);
        Bukkit.getServer().getPluginManager().registerEvents(new Damage(),this);
    }
    @Override
    public void onStop(){}
    Jump Jump;
}
/*
* Jump *DONE*
*
* In Progress:
* Particles
* Getting ideas
* */