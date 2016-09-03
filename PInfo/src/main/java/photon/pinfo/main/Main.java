package photon.pinfo.main;

import me.tigerhix.lib.scoreboard.ScoreboardLib;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import photon.pinfo.gui.ip.IPAPI;
import photon.pinfo.gui.tps.TPSBar;

public final class Main extends JavaPlugin {
    boolean safeMode=false;
    boolean running=false; // ASyncTPSBarUpdater()
    @Override
    public void onEnable(){
        this.saveDefaultConfig();
        ipapi=new IPAPI();
        Admins=new Admins();
        //TPS
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TPSBar(), 100L, 2L);
        if(!running){
            aSyncTPSBarUpdater();
        }
        // Scoreboard
        ScoreboardLib.setPluginInstance(this);
    }
    // Global*
    public static String infoPrefix="&6[&3AdminGUI&6] &7";
    public static String errPrefix="&6[&3AdminGUI&6] [ERROR] &c";
    // Classes
    IPAPI ipapi;
    Admins Admins;
    // Local*
    Player target;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) { //Null IPAPI:32 (no internet connection) **Fixed?? TODO: Test
        if (sender != null && (sender instanceof Player||!safeMode)) { //TODO: TPS XP bar (https://bukkit.org/threads/get-server-tps.143410/)
            target = (Player) sender;
            if (lbl.equalsIgnoreCase("whois")) {
                if(!Admins.isAdmin(target)){
                    send("@infoYou are now in admin mode. Use &b/admin &7to toggle.");
                    Admins.addAdmin(target);
                }else{
                    send("@infoThe data has been refreshed!");
                }
                ipapi.loadData(target);
            }else if(lbl.equalsIgnoreCase("admin")){
                if(Admins.isAdmin(target)){
                    Admins.removeAdmin(target);
                }else{
                    Admins.addAdmin(target);
                }
            }
        }
        return true;
    }
    public void aSyncTPSBarUpdater(){
        running=true;
        new BukkitRunnable(){
            public void run() {
                for(Player target:Admins.getAdmins()){
                    int barTPS;
                    target.setLevel((int)Math.floor(TPSBar.getTPS()));
                    System.out.println(TPSBar.getTPS());
                    target.setExp((float)TPSBar.getTPS()/20/10);
                }
            }
        }.runTaskTimerAsynchronously(this,0,5);
    }
    public void send(String...message){
        for(String line:message){
            if(line.contains("@err")){
                line=line.replace("@err","");
                line=Main.errPrefix+line;
            }else if(line.contains("@info")){
                line=line.replace("@info","");
                line=Main.infoPrefix+line;
            }
            target.sendMessage(ChatColor.translateAlternateColorCodes('&',line));
        }
    }
}