package net.arcanemc.hub.particles;
import net.arcanemc.hub.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ParticleManager {
    private Main Main;
    public ParticleManager(Main Main){
        this.Main=Main;
        if(active==null||active.isEmpty())active=new ArrayList<>();
    }
    private List<Player> active;
}