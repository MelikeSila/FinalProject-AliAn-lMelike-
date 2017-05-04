package Module;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mafy on 04.05.2017.
 */
@IgnoreExtraProperties
public class PostGame {

    public String gameId="";
    public String gameMakerId="";
    public String location_lat="";
    public String location_long="";
    public String photo="";
    public String date="";

    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public PostGame() {
        // Default constructor required for calls to DataSnapshot.getValue(PostGame.class)
    }

    public PostGame(String GId, String GMId, String lat, String longi, String p_url, String d) {
        this.gameId = GId;
        this.gameMakerId = GMId;
        this.location_lat = lat;
        this.location_long= longi;
        this.photo = p_url;
        this.date = d;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("gameId", gameId);
        result.put("gameMakerId", gameMakerId);
        result.put("location_latitude", location_lat);
        result.put("location_longitude", location_long);
        result.put("photourl", photo);
        result.put("date", date);
        return result;
    }
}
