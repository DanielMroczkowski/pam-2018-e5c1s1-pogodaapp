package pl.parzych_demianiuk.pogodaapp.longTerm;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.AppBarLayout;

import pl.parzych_demianiuk.pogodaapp.R;

public class LongTermActivity extends AppCompatActivity{

    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    double latt ;
    double longg;

    String width;
    String length;
    @Override
    protected void onCreate(Bundle savedInstatnceState){
        super.onCreate(savedInstatnceState);
        setContentView(R.layout.activity_long_term);
          }


    void getLocation() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        }else {

            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                latt = location.getLatitude();
                width= Double.toString(latt);
                longg = location.getLongitude();
                length = Double.toString(longg);

            }
        }
    }



}
