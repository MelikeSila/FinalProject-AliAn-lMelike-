package Module;

import com.google.android.gms.maps.model.LatLng;
 import org.parceler.Parcel;
/**
 * Created by mafy on 10.05.2017.
 */

@Parcel
public class UserModel  {
    public String userId;
    public double userLatitude;
    public double userLongitude;

    public UserModel(){

    }
    public UserModel(String photoUrl, double latitude, double longitude){
        this.userId = photoUrl;
        this.userLatitude = latitude;
        this.userLongitude = longitude;
    }
}
