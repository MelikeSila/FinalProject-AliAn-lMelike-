package Module;

import com.google.android.gms.maps.model.LatLng;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by mafy on 09.05.2017.
 */

public class LocationModel implements Parcelable {

    public double startLatitude;
    public double startLongitude;

    public LocationModel(){

    }
    public LocationModel(double startLatitude, double startLongitude){

        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
    }

    protected LocationModel(Parcel in) {
        startLatitude = in.readDouble();
        startLongitude = in.readDouble();
    }

    public static final Creator<LocationModel> CREATOR = new Creator<LocationModel>() {
        @Override
        public LocationModel createFromParcel(Parcel in) {
            return new LocationModel(in);
        }

        @Override
        public LocationModel[] newArray(int size) {
            return new LocationModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(startLatitude);
        dest.writeDouble(startLongitude);
    }
}
