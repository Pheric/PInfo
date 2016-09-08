package photon.pinfo.main;

import org.bukkit.entity.Player;
import photon.pinfo.gui.main.GUI;

import java.util.ArrayList;
import java.util.List;

class Admins {
    private Main Main;
    Admins(Main Main,GUI gui){
        this.Main=Main;
        this.gui=gui;
    }
    private GUI gui;
    List<Player> admins=new ArrayList<Player>();
    void addAdmin(Player target){
        if(!admins.contains(target)){
            admins.add(target);
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
    void removeAdmin(Player target){
        if(admins.contains(target)){
            admins.remove(target);
            gui.removeGUI(target);
            target.setLevel(Main.getConfig().getInt(target.getUniqueId()+".level"));
            target.setExp((float)Main.getConfig().getDouble(target.getUniqueId()+".exp"));
            Main.getConfig().set("admins."+target.getUniqueId(),"");
            Main.saveConfig();
            Main.reloadConfig();
        }
    }
    boolean isAdmin(Player target){
        if(admins.contains(target))return true;
        return false;
    }
    List<Player> getAdmins(){return admins;}
}
