package pl.parzych_demianiuk.pogodaapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD
=======
import android.text.InputType;
>>>>>>> cd2cbcd9f6a00805451bbdcd87286a0728d5eab1
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pl.parzych_demianiuk.pogodaapp.longTerm.LongTermActivity;
import pl.parzych_demianiuk.pogodaapp.longTerm.WeatherActivityFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, updatedField, wind_field, pogodaField;
    ImageView imageView;
    int []icons = {R.drawable.cloud, R.drawable.rainy, R.drawable.snowing, R.drawable.sun,R.drawable.thunderstorm,R.drawable.wind};
    int []backgrounds = {R.drawable.wiosna, R.drawable.lato, R.drawable.jesien, R.drawable.zima};
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    double latti ;
    double longi ;
    LinearLayout rl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


       getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        rl = (LinearLayout) findViewById(R.id.activity_main);
        cityField = (TextView) findViewById(R.id.cityText);
        updatedField = (TextView) findViewById(R.id.updateText);
        detailsField = (TextView) findViewById(R.id.cloudText);
        currentTemperatureField = (TextView) findViewById(R.id.tempText);
        humidity_field = (TextView) findViewById(R.id.humidityText);
        pressure_field = (TextView) findViewById(R.id.pressureText);
        wind_field = (TextView) findViewById(R.id.windText);
        pogodaField = (TextView) findViewById(R.id.pogodaText);
       imageView = (ImageView) findViewById(R.id.weatherView);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();
        String szerokosc = Double.toString(latti);
        String dlugosc = Double.toString(longi);



        SimpleDateFormat df = new SimpleDateFormat("MM-dd");
        String date = df.format(Calendar.getInstance().getTime());






        Weather.placeIdTask asyncTask = new Weather.placeIdTask(new Weather.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_wind, String weather_updatedOn, String weatherDescription) {





                cityField.setText(weather_city);
                updatedField.setText(weather_updatedOn);
                detailsField.setText("Zachmurzenie: " + weather_description);
                currentTemperatureField.setText(weather_temperature+"C");
                humidity_field.setText("Wilgotność: " + weather_humidity);
                pressure_field.setText("Ciśnienie: " + weather_pressure);
                wind_field.setText("Wiatr: " + weather_wind);
                //weatherDescription = "light rain";
                pogodaField.setText(weatherDescription);





                if(weatherDescription.equals("LIGHT RAIN") || weatherDescription.equals("MODERATE RAIN") || weatherDescription.equals("HEAVY INTENSITY RAIN") || weatherDescription.equals("VERY HEAVY RAIN") || weatherDescription.equals("EXTREME RAIN") || weatherDescription.equals("FREEZING RAIN") || weatherDescription.equals("LIGHT INTENSITY SHOWER RAIN") || weatherDescription.equals("SHOWER RAIN") || weatherDescription.equals("HEAVY INTENSITY SHOWER RAIN") || weatherDescription.equals("RAGGED SHOWER RAIN")){
                    imageView.setImageResource(icons[1]);
                    pogodaField.setText("Deszcz");
                }else if(weatherDescription.equals("CLEAR SKY")){
                    pogodaField.setText("Czyste niebo");
                    imageView.setImageResource(icons[3]);
                }else if(weatherDescription.equals("THUNDERSTORM WITH LIGHT RAIN") || weatherDescription.equals("THUNDERSTORM WITH RAIN") || weatherDescription.equals("THUNDERSTORM WITH HEAVY RAIN") || weatherDescription.equals("LIGHT THUNDERSTORM") || weatherDescription.equals("THUNDERSTORM") || weatherDescription.equals("HEAVY THUNDERSTORM") || weatherDescription.equals("RAGGED THUNDERSTORM") || weatherDescription.equals("THUNDERSTORM WITH LIGHT DRIZZLE") || weatherDescription.equals("THUNDERSTORM WITH DRIZZLE") || weatherDescription.equals("THUNDERSTORM WITH HEAVY DRIZZLE")){
                    pogodaField.setText("Burza");
                    imageView.setImageResource(icons[4]);
                }else if(weatherDescription.equals("FEW CLOUDS") || weatherDescription.equals("SCATTERED CLOUDS") || weatherDescription.equals("BROKEN CLOUDS") || weatherDescription.equals("OVERCAST CLOUDS") ){
                    pogodaField.setText("Zachmurzone niebo");
                    imageView.setImageResource(icons[0]);
                }else if(weatherDescription.equals("LIGHT SNOW") || weatherDescription.equals("SNOW") || weatherDescription.equals("HEAVY SNOW") || weatherDescription.equals("SLEET") || weatherDescription.equals("SHOWER SLEET") || weatherDescription.equals("LIGHT RAIN AND SNOW") || weatherDescription.equals("RAIN AND SNOW") || weatherDescription.equals("LIGHT SHOWER SNOW") || weatherDescription.equals("SHOWER SNOW") || weatherDescription.equals("HEAVY SHOWER SNOW")){
                    pogodaField.setText("Śnieg");
                    imageView.setImageResource(icons[2]);
                } else { imageView.setImageResource(icons[4]);}



            }

        }
        );
        asyncTask.execute(szerokosc, dlugosc);


        //asyncTask.execute("52.229676", "21.012228999999934");
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


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_program:
                Intent i = new Intent(MainActivity.this, AboutProgramActivity.class);
                startActivity(i);
                return true;
                case R.id.longTerm:
                Intent j = new Intent(MainActivity.this, LongTermActivity.class);
                startActivity(j);
                return true;

                default:
            return super.onOptionsItemSelected(item);
        }
    }
}

