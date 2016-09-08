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
import photon.pinfo.gui.main.GUI;
import photon.pinfo.gui.tps.TPSBar;

public final class Main extends JavaPlugin {
    private boolean safeMode=false;
    private boolean aRunning=false; // ASyncUpdater()
    private boolean sRunning=false; // syncUpdater()
    @Override
    public void onEnable(){
        this.saveDefaultConfig();
        IPAPI ipapi=new IPAPI(this);
        gui=new GUI(this,ipapi);
        Admins=new Admins(this,gui);
        // Updating data
        syncUpdater();
        if(!aRunning){
            aSyncUpdater();
        }
        if(!sRunning){
            syncUpdater();
        }
        // ScoreboardMain
        ScoreboardLib.setPluginInstance(this);
    }
    // Global*
    public static String infoPrefix="&6[&3AdminGUI&6] &7";
    public static String errPrefix="&6[&3AdminGUI&6] [ERROR] &c";
    // Classes
    private Admins Admins;
    private IPAPI ipapi;
    private GUI gui;
    // Local*
    private Player target;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) { //Null IPAPI:32 (no internet connection) **Fixed?? TODO: Test
        if (sender != null && (sender instanceof Player||!safeMode)) {
            // DEVELOPMENT FEATURE -------
            if(!(sender instanceof Player)){
                target=Bukkit.getPlayer("Photon156");
            }else
            // ---------------------------
            target = (Player) sender;
            if (lbl.equalsIgnoreCase("whois")) {
                if(!Admins.isAdmin(target)){
                    send("@infoYou are now in admin mode. Use &b/admin &7to toggle.");
                    send("@infoAdmin mode activated");
                    Admins.addAdmin(target);
                }else{
                    Admins.removeAdmin(target);
                    Admins.addAdmin(target);
                    send("@infoThe data has been refreshed!");
                }
            }else if(lbl.equalsIgnoreCase("admin")){
                if(Admins.isAdmin(target)){
                    Admins.removeAdmin(target);
                    send("@infoAdmin mode deactivated");
                }else{
                    Admins.addAdmin(target);
                    send("@infoAdmin mode activated");
                }
            }
        }
        return true;
    }
    private void aSyncUpdater(){
        aRunning=true;
        new BukkitRunnable(){
            public void run() {
                for(Player target:Admins.getAdmins()){
                    // TPS Bar
                    target.setLevel((int)Math.floor(TPSBar.getTPS()));
                    target.setExp((float)TPSBar.getTPS()/100*5);
                }
            }
        }.runTaskTimerAsynchronously(this,0,5);
    }
    private void syncUpdater(){
        sRunning=true;
        new BukkitRunnable(){
            public void run(){
                // Scoreboard
                for(Player target:Admins.getAdmins()){
                    gui.setGUIWithOldData(target);
                }
            }
        }.runTaskTimer(this,0L,10L);
    }
    private void send(String...message){
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