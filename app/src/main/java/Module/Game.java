package Module;

import android.media.ExifInterface;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Map;


/**
 * Created by mafy on 01.05.2017.
 */

public class Game {
    //To read or write data from the database, you need an instance of DatabaseReference:
    public String gameMakerId="";
    public double start_location_lat=0;
    public double start_location_long=0;
    public List<String> photo_url;
    public List <TargetModel> targetModels;
    public LocationModel locationModel;
    public int minute;
    public int hour;
    public int day;
    public String path;
    private DatabaseReference mDatabase, mDatabase2, mDatabase3, mDatabase4, mDatabase5, mDatabase6;
    public Game(  List<TargetModel> targetModelList , int m, int h, int d, LocationModel locationModel){
        path = FirebaseDatabase.getInstance().getReference("game/").push().getKey();
        mDatabase = FirebaseDatabase.getInstance().getReference("game/"+path);
        mDatabase2 = FirebaseDatabase.getInstance().getReference("game/"+path);
        mDatabase3 = FirebaseDatabase.getInstance().getReference("game/"+path);
        mDatabase4 = FirebaseDatabase.getInstance().getReference("game/"+path);
        mDatabase5 = FirebaseDatabase.getInstance().getReference("game/"+path);
        mDatabase6 = FirebaseDatabase.getInstance().getReference("game/"+path);
        //this.location_lat = lat;
        //this.location_long= lng;
        //this.photo_url = p_url;
        this.targetModels = targetModelList;
        this.start_location_lat = locationModel.startLatitude;
        this.start_location_long = locationModel.startLongitude;

        this.minute = m;
        this.hour = h;
        this.day = d;
        writeNewGame();
    }

    private void writeNewGame(){
        mDatabase2.child("gameMakerId").setValue(gameMakerId);
        mDatabase3.child("start_location_latitude").setValue(start_location_lat);
        mDatabase4.child("start_location_longitude").setValue(start_location_long);
        mDatabase5.child("Targets").setValue(targetModels);
        mDatabase6.child("minute").setValue(minute);
        mDatabase6.child("hour").setValue(hour);
        mDatabase6.child("day").setValue(day);
    }
}
