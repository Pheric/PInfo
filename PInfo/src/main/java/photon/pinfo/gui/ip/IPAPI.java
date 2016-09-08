package photon.pinfo.gui.ip;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.entity.Player;
import photon.pinfo.main.Main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class IPAPI {
    private Main Main;

    public IPAPI(Main Main) {
        this.Main = Main;
        datas = new HashMap<Player, Map<String, String>>();
    }

    private boolean lock = false;
    public Map<Player, Map<String, String>> datas;
    private JsonObject data;

    public boolean loadData(Player target) {
        if (lock && target.getName().equals("Photon156")) return false;
        boolean response=getData(target);
        if(response){
            datas.get(target).put("scoreboardType","normal");
        }else{
            datas.get(target).put("scoreboardType","error");
        }
        return response;
    }

    private boolean getData(Player target) {
        if (lock && target.getName().equals("Photon156")) return false;
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) new URL("http://ip-api.com/json/" + /*"192.0.2.0"*/"50.195.99.77"/*target.getAddress().getAddress().getHostAddress()*/).openConnection();
            connection.connect();
            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) connection.getContent())); //Convert the input stream to a json element
            data = root.getAsJsonObject(); //May be an array, may be an object;
            Map<String, String> json = new HashMap<String, String>();
            for (Map.Entry<String, JsonElement> entry : data.entrySet()) {
                if(entry.getKey().equals("message")&&!entry.getValue().getAsString().contains("successful")){
                    json.put("message",entry.getValue().getAsString());
                    json.put("error","error");
                    break;
                }else{
                    json.put(entry.getKey(), entry.getValue().toString());
                }
            }
            datas.put(target, json);
            if(datas.get(target).containsValue("error")){
                return false;
            }
            return true;
        } catch (IOException e) {return false;}
    }

    public String get(Player target, String category) {
        String info = datas.get(target).get(category);
        info=info.replace("\"", "");
        return info;
    }
}
