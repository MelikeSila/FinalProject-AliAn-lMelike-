package Module;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mafy on 01.05.2017.
 */

public class Game {
    //To read or write data from the database, you need an instance of DatabaseReference:

    public String GameId="";
    public String GameMakerId="";

    private DatabaseReference mDatabase, mDatabase2;

    public Game(String GId, String GMId){
        mDatabase = FirebaseDatabase.getInstance().getReference("game");
        mDatabase2 = FirebaseDatabase.getInstance().getReference("game");
        this.GameId = GId;
        this.GameMakerId = GMId;
        writeNewGame(GId, GMId);
    }

    private void writeNewGame(String gameId, String gameMakerId){
        mDatabase.child("gameId").setValue(gameId);
        mDatabase2.child("gameMaker").child("gameMakerId").setValue(gameMakerId);
    }

}
