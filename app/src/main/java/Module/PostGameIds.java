package Module;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mafy on 07.05.2017.
 */

public class PostGameIds {


    public String game_Id="";

    public PostGameIds() {
        // Default constructor required for calls to DataSnapshot.getValue(PostGame.class)
    }

    public PostGameIds(String GId) {
        this.game_Id = GId;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("game_Id", game_Id);
        return result;
    }

}
