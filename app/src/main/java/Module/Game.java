package Module;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

/**
 * Created by mafy on 01.05.2017.
 */

public class Game {
    //To read or write data from the database, you need an instance of DatabaseReference:

    public String gameId="";
    public String gameMakerId="";
    public double location_lat=0;
    public double location_long=0;
    public String photo="";
    public int minute;
    public int hour;
    public int day;

    private DatabaseReference mDatabase, mDatabase2, mDatabase3, mDatabase4, mDatabase5, mDatabase6;
    public Game(String GId, String GMId, double lat, double lng, String p_url, int m, int h, int d ){

        String path = FirebaseDatabase.getInstance().getReference("game/").push().getKey();
        mDatabase = FirebaseDatabase.getInstance().getReference("game/"+path);
        mDatabase2 = FirebaseDatabase.getInstance().getReference("game/"+path);
        mDatabase3 = FirebaseDatabase.getInstance().getReference("game/"+path);
        mDatabase4 = FirebaseDatabase.getInstance().getReference("game/"+path);
        mDatabase5 = FirebaseDatabase.getInstance().getReference("game/"+path);
        mDatabase6 = FirebaseDatabase.getInstance().getReference("game/"+path);
        this.gameId = GId;
        this.gameMakerId = GMId;
        this.location_lat = lat;
        this.location_long= lng;
        this.photo = p_url;
        this.minute = m;
        this.hour = h;
        this.day = d;
        writeNewGame();
    }

    private void writeNewGame(){
        //mDatabase.child("gameId").setValue(gameId);
        mDatabase2.child("gameMakerId").setValue(gameMakerId);
        mDatabase3.child("location_latitude").setValue(location_lat);
        mDatabase4.child("location_longitude").setValue(location_long);
        mDatabase5.child("photourl").setValue(photo);
        mDatabase6.child("minute").setValue(minute);
        mDatabase6.child("hour").setValue(hour);
        mDatabase6.child("day").setValue(day);
    }

}
