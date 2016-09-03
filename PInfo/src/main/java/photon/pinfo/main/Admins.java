package photon.pinfo.main;

import org.bukkit.entity.Player;
import photon.pinfo.gui.main.GUI;

import java.util.ArrayList;
import java.util.List;

public class Admins {
    private Main Main;
    public void Admins(Main Main){
        this.Main=Main;
    }
    long updateInterval=20;
    List<Player> admins=new ArrayList<Player>();
    public void addAdmin(Player target){
        if(!admins.contains(target)){
            admins.add(target);
            GUI gui=new GUI();
            gui.setGUI(target);
            if (target.getLevel() != 0) {
                Main.getConfig().set("admins." + target.getUniqueId() + ".level", target.getLevel());
            }
            if (target.getExp() != 0) {
                Main.getConfig().set("admins." + target.getUniqueId() + ".exp", target.getExp());
            }
            Main.saveConfig();
            Main.reloadConfig();
        }
    }
    public void removeAdmin(Player target){
        if(admins.contains(target)){
            admins.remove(target);
            GUI gui=new GUI();
            gui.removeGUI(target);
            target.setLevel(Main.getConfig().getInt(target.getUniqueId()+".level"));
            target.setExp((float)Main.getConfig().getDouble(target.getUniqueId()+".exp"));
            Main.getConfig().set("admins."+target.getUniqueId(),"");
            Main.saveConfig();
            Main.reloadConfig();
        }
    }
    public boolean isAdmin(Player target){
        if(admins.contains(target))return true;
        return false;
    }
    public List<Player> getAdmins(){return admins;}
}
