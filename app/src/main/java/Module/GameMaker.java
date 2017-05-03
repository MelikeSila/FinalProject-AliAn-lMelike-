package Module;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mafy on 01.05.2017.
 */

public class GameMaker {
    //To read or write data from the database, you need an instance of DatabaseReference:

    public String GameMakerId="";
    public String GameId="";
    private DatabaseReference mDatabase, mDatabase2;

    public GameMaker(String GId, String GMId){
        mDatabase = FirebaseDatabase.getInstance().getReference("gameMaker");
        mDatabase2 = FirebaseDatabase.getInstance().getReference("gameMaker");
        this.GameId = GId;
        this.GameMakerId = GMId;
        writeNewGame(GId, GMId);
    }

    private void writeNewGame(String gameId, String gameMakerId){
        mDatabase2.child("gameMakerId").setValue(gameMakerId);
        mDatabase.child("gameId").setValue(gameId);
    }
}
