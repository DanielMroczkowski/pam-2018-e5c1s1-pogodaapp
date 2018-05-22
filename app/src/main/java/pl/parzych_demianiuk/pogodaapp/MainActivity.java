package pl.parzych_demianiuk.pogodaapp;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, updatedField, wind_field, pogodaField;
    ImageView imageView;
    int []icons = {R.drawable.cloud, R.drawable.rainy, R.drawable.snowing, R.drawable.sun,R.drawable.thunderstorm,R.drawable.wind};
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    double latti ;
    double longi ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


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




        com.faultinmycode.fimcweatherapp.Weather.placeIdTask asyncTask = new com.faultinmycode.fimcweatherapp.Weather.placeIdTask(new com.faultinmycode.fimcweatherapp.Weather.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_wind, String weather_updatedOn, String weatherDescription) {



                cityField.setText(weather_city);
                updatedField.setText(weather_updatedOn);
                detailsField.setText("Zachmurzenie: " + weather_description);
                currentTemperatureField.setText("Temperatura: " + weather_temperature);
                humidity_field.setText("Wilgotność: " + weather_humidity);
                pressure_field.setText("Ciśnienie: " + weather_pressure);
                wind_field.setText("Wiatr: " + weather_wind);
                //weatherDescription = "light rain";
                pogodaField.setText(weatherDescription);





                if(weatherDescription.equals("light rain") || weatherDescription.equals("moderate rain") || weatherDescription.equals("heavy intensity rain") || weatherDescription.equals("very heavy rain") || weatherDescription.equals("extreme rain") || weatherDescription.equals("freezing rain") || weatherDescription.equals("light intensity shower rain") || weatherDescription.equals("shower rain") || weatherDescription.equals("heavy intensity shower rain") || weatherDescription.equals("ragged shower rain")){
                    imageView.setImageResource(icons[1]);
                }else if(weatherDescription.equals("CLEAR SKY")){
                    imageView.setImageResource(icons[3]);
                }else if(weatherDescription.equals("thunderstorm with light rain") || weatherDescription.equals("thunderstorm with rain") || weatherDescription.equals("thunderstorm with heavy rain") || weatherDescription.equals("light thunderstorm") || weatherDescription.equals("thunderstorm") || weatherDescription.equals("heavy thunderstorm") || weatherDescription.equals("ragged thunderstorm") || weatherDescription.equals("thunderstorm with light drizzle") || weatherDescription.equals("thunderstorm with drizzle") || weatherDescription.equals("thunderstorm with heavy drizzle")){
                    imageView.setImageResource(icons[4]);
                }else if(weatherDescription.equals("few clouds") || weatherDescription.equals("scattered clouds") || weatherDescription.equals("broken clouds") || weatherDescription.equals("overcast clouds") ){
                    imageView.setImageResource(icons[0]);
                }else if(weatherDescription.equals("light snow") || weatherDescription.equals("snow") || weatherDescription.equals("heavy snow") || weatherDescription.equals("sleet") || weatherDescription.equals("shower sleet") || weatherDescription.equals("light rain and snow") || weatherDescription.equals("rain and snow") || weatherDescription.equals("light shower snow") || weatherDescription.equals("shower snow") || weatherDescription.equals("heavy shower snow")){
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
                Intent j = new Intent(this, LongTermActivity.class);
                startActivity(j);
                return true;

                default:
            return super.onOptionsItemSelected(item);
        }
    }
}

