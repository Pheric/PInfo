package photon.pinfo.gui.main;

import com.google.gson.JsonObject;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import photon.pinfo.main.Main;
import photon.pinfo.gui.Scoreboard.Scoreboard;

public class GUI {
    Player target;
    public void setGUI(Player target){
        this.target=target;
        send("@infoAdmin mode activated!");
    }
    public void removeGUI(Player target){
        this.target=target;
        send("@infoAdmin mode deactivated!");
    }
    public void failed(Player target,JsonObject data){
        this.target=target;
        send("@errI failed to retrieve the information you requested.","@errPlease send this to your staff team:","@err"+data.get("message").getAsString());
        Scoreboard sb=new Scoreboard();
        sb.setScoreboardError(target);
    }

    public void send(String...message){
        for(String line:message){
            if(line.contains("@err")){
                line=line.replace("@err","");
                line= Main.errPrefix+line;
            }else if(line.contains("@info")){
                line=line.replace("@info","");
                line=Main.infoPrefix+line;
            }
            target.sendMessage(ChatColor.translateAlternateColorCodes('&',line));
        }
    }
}
