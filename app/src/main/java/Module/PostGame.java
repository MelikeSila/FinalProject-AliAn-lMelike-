package Module;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mafy on 04.05.2017.
 */
@IgnoreExtraProperties
public class PostGame {

    public String gameId="";
    public String gameMakerId="";
    public double start_location_latitude = 0.0;
    public double start_location_longitude=0.0;
    public double location_latitude = 0.0;
    public double location_longitude=0.0;
    public String photo_url;
    public String date="";

    public PostGame() {
        // Default constructor required for calls to DataSnapshot.getValue(PostGame.class)
    }

    public PostGame(String GId, String GMId, String lat, String longi, String p_url, String d) {
        this.gameId = GId;
        this.gameMakerId = GMId;
        this.start_location_latitude = Double.parseDouble(lat);
        this.start_location_longitude= Double.parseDouble(longi);
        this.location_latitude = Double.parseDouble(lat);
        this.location_longitude= Double.parseDouble(longi);
        this.photo_url = p_url;
        this.date = d;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("gameId", gameId);
        result.put("gameMakerId", gameMakerId);
        result.put("start_location_latitude", start_location_latitude);
        result.put("start_location_longitude", start_location_longitude);
        result.put("location_latitude", location_latitude);
        result.put("location_longitude", location_longitude);
        result.put("photo_url", photo_url);
        result.put("date", date);
        return result;
    }
}
