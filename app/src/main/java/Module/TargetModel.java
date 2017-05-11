package Module;

import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcel;

/**
 * Created by mafy on 08.05.2017.
 */
@Parcel
public class TargetModel  {
    public String photoUrl;
    public double targetLatitude;
    public double targetLongitude;

    public TargetModel(){

    }
    public TargetModel(String photoUrl, double latitude, double longitude){
        this.photoUrl = photoUrl;
        this.targetLatitude = latitude;
        this.targetLongitude = longitude;
    }
}
