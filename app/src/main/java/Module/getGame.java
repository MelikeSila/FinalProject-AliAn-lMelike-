package Module;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mafy on 04.05.2017.
 */

public class getGame {
    public String gameId="";
    public String gameMakerId="";
    public String location_lat="";
    public String location_long="";
    public String photo="";
    public String date="";
    public String la, lo;

    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();
    DatabaseReference mPostReference;
    public getGame() {

        mPostReference = FirebaseDatabase.getInstance().getReference("game/GId");
        mPostReference.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to Firebase
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
                la = (String) newPost.get(location_lat);
                lo = (String) newPost.get(location_long);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
            //... ChildEventListener also defines onChildChanged, onChildRemoved,
            //    onChildMoved and onCanceled, covered in later sections.
        });
        // Default constructor required for calls to
        //this.gameId = GId;
        //this.gameMakerId = GMId;
        this.location_lat = la;
        this.location_long= lo;
        //this.photo = p_url;
        //this.date = d;
    }
}
