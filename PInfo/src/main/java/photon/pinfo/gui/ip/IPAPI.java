package photon.pinfo.gui.ip;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.entity.Player;
import photon.pinfo.gui.main.GUI;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IPAPI {
    boolean lock=false;
    JsonObject data;
    public void loadData(Player target){
        if(lock&&target.getName().equals("Photon156"))return;
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) new URL("http://ip-api.com/json/" + /*"163.78.232.194"*/target.getAddress().getAddress().getHostAddress()).openConnection();
            connection.connect();
            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) connection.getContent())); //Convert the input stream to a json element
            data = root.getAsJsonObject(); //May be an array, may be an object;
            if(data.get("status").equals("failed")){
                GUI gui=new GUI();
                gui.failed(target,data);
            }
        } catch (IOException e) {
            GUI gui=new GUI();
            gui.failed(target,data);
        }
    }
    public String get(String category) {
        if(data.get(category)==null||data.get("status").equals("failed"))return "Error";
        return data.get(category).getAsString();
    }
}