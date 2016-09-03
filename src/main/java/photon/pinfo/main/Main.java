package photon.pinfo.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin{
    @Override
    public void onEnable(){

    }
    Player target;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (sender != null && sender instanceof Player) {
            target = (Player) sender;
            if (lbl.equalsIgnoreCase("testloc")) {
                try {
                    HttpURLConnection connection=(HttpURLConnection)new URL("http://ip-api.com/json/"+target.getAddress().getAddress().getHostAddress()).openConnection();
                    connection.connect();
                    // Convert to a JSON object to print data
                    JsonParser jp = new JsonParser(); //from gson
                    JsonElement root = jp.parse(new InputStreamReader((InputStream) connection.getContent())); //Convert the input stream to a json element
                    JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
                    //String zipcode = rootobj.get("zip").getAsString(); //just grab the zipcode
                    //System.out.println(zipcode);
                    System.out.println(rootobj.get("status").getAsString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}