package Module;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mafy on 04.05.2017.
 */

public class PlayedGame {
    //To read or write data from the database, you need an instance of DatabaseReference:

    public String playedGameId="";
    public String gameId="";
    public String userId="";
    public String score="";

    private DatabaseReference mDatabase1, mDatabase2, mDatabase3;

    public PlayedGame(){
        //Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public PlayedGame(String playedGI, String gI, String uI, String s ){
        mDatabase1 = FirebaseDatabase.getInstance().getReference("PlayedGames");
        mDatabase2 = FirebaseDatabase.getInstance().getReference("PlayedGames");
        mDatabase3 = FirebaseDatabase.getInstance().getReference("PlayedGames");
        this.playedGameId = playedGI;
        this.gameId=gI;
        this.userId=uI;
        this.score=s;
        writeNewPlayedGame();
    }

    private void writeNewPlayedGame(){
        mDatabase1.child(playedGameId).child("gameId").setValue(gameId);
        mDatabase2.child(playedGameId).child("userId").setValue(userId);
        mDatabase3.child(playedGameId).child("score").setValue(score);
    }
}
