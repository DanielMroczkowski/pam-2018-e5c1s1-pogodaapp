package pl.parzych_demianiuk.pogodaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class LongTermActivity extends AppCompatActivity {

    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, updatedField, wind_field;
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    double latti;
    double longi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
       // getActionBar().setDisplayHomeAsUpEnabled(true);


        cityField = (TextView) findViewById(R.id.Text1);
        updatedField = (TextView) findViewById(R.id.Text5);
        detailsField = (TextView) findViewById(R.id.Text4);
        currentTemperatureField = (TextView) findViewById(R.id.Text2);
        humidity_field = (TextView) findViewById(R.id.humidityText);
        pressure_field = (TextView) findViewById(R.id.pressureText);
        wind_field = (TextView) findViewById(R.id.Text3);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();
        String szerokosc = Double.toString(latti);
        String dlugosc = Double.toString(longi);


        com.faultinmycode.fimcweatherapp.Weather.placeIdTask asyncTask = new com.faultinmycode.fimcweatherapp.Weather.placeIdTask(new com.faultinmycode.fimcweatherapp.Weather.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_wind, String weather_updatedOn, String weatherDescription) {

                cityField.setText(weather_city);
                updatedField.setText(weather_updatedOn);
                detailsField.setText("Zachmurzenie: " + weather_description);
                currentTemperatureField.setText("Temperatura: " + weather_temperature);
                humidity_field.setText("Wilgotność: " + weather_humidity);
                pressure_field.setText("Ciśnienie: " + weather_pressure);
                wind_field.setText("Wiatr: " + weather_wind);


            }

        });
    }

    void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        }else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                latti = location.getLatitude();
                longi = location.getLongitude();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_program:
                Intent i = new Intent(LongTermActivity.this, AboutProgramActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/


}
