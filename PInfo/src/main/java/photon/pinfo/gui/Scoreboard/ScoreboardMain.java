package photon.pinfo.gui.Scoreboard;

import me.tigerhix.lib.scoreboard.ScoreboardLib;
import me.tigerhix.lib.scoreboard.common.EntryBuilder;
import me.tigerhix.lib.scoreboard.type.Entry;
import me.tigerhix.lib.scoreboard.type.Scoreboard;
import me.tigerhix.lib.scoreboard.type.ScoreboardHandler;
import org.bukkit.entity.Player;
import photon.pinfo.gui.ip.IPAPI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardMain {
    private IPAPI ipapi;
    public ScoreboardMain(IPAPI ipapi){
        this.ipapi=ipapi;
        scoreboards=new HashMap<Player,Scoreboard>();
    }
    private Map<Player,Scoreboard> scoreboards;
    public void setScoreboardNormal(Player target){
        Scoreboard scoreboard = ScoreboardLib.createScoreboard(target).setHandler(new ScoreboardHandler() {
            public String getTitle(Player player) {
                return null;
            }
            public List<Entry> getEntries(Player player) {
                // Logic
                String isOp=player.isOp()?"&4Operator":"&7Operator";
                String flying=player.isFlying()?"&aFlying":"&7Flying";
                String gm1=player.getGameMode().toString().equals("CREATIVE")?"&aCreative":"&7Survival";
                return new EntryBuilder()
                        .next("&a&lPlayer (nick)")
                        .next("&e"+player.getName()+" ("+player.getDisplayName()+")")
                        .blank()
                        .next("&a&lIP Address")
                        .next("&6"+ipapi.get(player,"query"))
                        .blank()
                        .next("&c&lReal Location")
                        .next("&6&o"+ipapi.get(player,"city")+", "+ipapi.get(player,"region")+", "+ipapi.get(player,"country")+" ("+ipapi.get(player,"countryCode")+")")
                        .blank()
                        .next("&a&lTimezone")
                        .next("&e"+ipapi.get(player,"timezone"))
                        .blank()
                        .next("  "+isOp+"  "+flying)
                        .next("       "+gm1+"      ")
                        .build();
            }
        }).setUpdateInterval(2000L);
        scoreboard.activate();
        scoreboards.put(target,scoreboard);
    }
    public void setScoreboardError(Player target){
        Scoreboard scoreboard = ScoreboardLib.createScoreboard(target).setHandler(new ScoreboardHandler() {
                    public String getTitle(Player player) {
                        return null;
                    }
                    public List<Entry> getEntries(Player player) {
                        // Logic
                        String isOp=player.isOp()?"&4Operator":"&7Operator";
                        String flying=player.isFlying()?"&aFlying":"&7Flying";
                        String gm1=player.getGameMode().toString().equals("CREATIVE")?"&aCreative":"&7Survival";
                        return new EntryBuilder()
                                .next("&4&lError")
                                .blank()
                                .next("&a&lPlayer (nick)")
                                .next("&e"+player.getName()+" ("+player.getDisplayName()+")")
                                .blank()
                                .next("&a&lIP Address")
                                .next("&6"+player.getAddress().getAddress().getHostAddress())
                                .blank()
                                .next("  "+isOp+"  "+flying)
                                .next("       "+gm1+"      ")
                                .build();
                    }
                }).setUpdateInterval(2000L);
        scoreboard.activate();
        scoreboards.put(target,scoreboard);
    }
    public void removeScoreboard(Player target){
        scoreboards.get(target).deactivate();
        scoreboards.remove(target);
    }
}