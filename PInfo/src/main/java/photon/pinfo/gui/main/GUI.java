package photon.pinfo.gui.main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import photon.pinfo.gui.Scoreboard.ScoreboardMain;
import photon.pinfo.gui.ip.IPAPI;
import photon.pinfo.main.Main;

public class GUI {
    private IPAPI ipapi;
    private Main Main;
    public GUI(Main Main,IPAPI ipapi){
        this.Main=Main;
        this.ipapi=ipapi;
        sb=new ScoreboardMain(ipapi);
    }
    private ScoreboardMain sb;
    private Player target;
    public void setGUI(Player target){
        this.target=target;
        if(ipapi.loadData(target)){
            sb.setScoreboardNormal(target);
        }else{
            failed(target);
        }
    }
    public void setGUIWithOldData(Player target){
        if(ipapi.datas.get(target).get("scoreboardType").equals("normal")){
            sb.setScoreboardNormal(target);
        }else if(ipapi.datas.get(target).get("scoreboardType").equals("error")){
            sb.setScoreboardError(target);
        }
    }
    public void removeGUI(Player target){
        this.target=target;
        sb.removeScoreboard(target);
    }
    public void failed(Player target){
        this.target=target;
        send("@errI failed to retrieve the IP information in the scoreboard.");
        send("@errError message: "+ipapi.datas.get(target).get("message"));
        sb.setScoreboardError(target);
    }

    private void send(String...message){
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
