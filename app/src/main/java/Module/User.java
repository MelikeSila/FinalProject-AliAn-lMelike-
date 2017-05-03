package Module;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mafy on 01.05.2017.
 */

public class User {
    //To read or write data from the database, you need an instance of DatabaseReference:

    public String userId="";
    public String userName="";
    private DatabaseReference mDatabase, mDatabase2;

    public User(String UId, String UName){
        //mDatabase = FirebaseDatabase.getInstance().getReference("user");
        mDatabase2 = FirebaseDatabase.getInstance().getReference("users");
        this.userId = UId;
        this.userName = UName;
        writeNewGame(UId, UName);
    }

    private void writeNewGame(String UId, String uName){
        mDatabase2.child(UId).child("userName").setValue(uName);
        //mDatabase.push().child("userId").setValue(UId);
    }
}
