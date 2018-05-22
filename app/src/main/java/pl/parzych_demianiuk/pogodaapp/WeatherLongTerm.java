package pl.parzych_demianiuk.pogodaapp;

import android.os.AsyncTask;

import com.faultinmycode.fimcweatherapp.Weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


    public class WeatherLongTerm {

        private static final String OPEN_WEATHER_MAP_URL =
                "http://api.openweathermap.org/data/2.5/forecast?lat=35&lon=139?&units=metric";

        private static final String OPEN_WEATHER_MAP_API = "0f40b9a2fd7aced5d29df69a4d963357";


        public interface AsyncResponse {

            void processFinish(String output1, String output2, String output3, String output4, String output5, String output6, String output7, String output8);
        }


        public static class placeIdTask extends AsyncTask<String, Void, JSONObject> {

            public Weather.AsyncResponse delegate = null;


            public placeIdTask(Weather.AsyncResponse asyncResponse) {
                delegate = asyncResponse;

            }

            @Override
            protected JSONObject doInBackground(String... params) {

                JSONObject jsonWeather = null;
                try {
                    jsonWeather = getWeatherJSON(params[0], params[1]);
                } catch (Exception e) {

                }
                return jsonWeather;
            }

            @Override
            protected void onPostExecute(JSONObject json1) {
                try {
                    if (json1 != null) {
                        JSONObject details = json1.getJSONArray("weather").getJSONObject(0);
                        JSONObject main = json1.getJSONObject("main");
                        JSONObject wind = json1.getJSONObject("wind");
                        JSONObject clouds = json1.getJSONObject("clouds");
                        DateFormat df = DateFormat.getDateTimeInstance();


                        String weather_city = json1.getString("name").toUpperCase(Locale.US) + ", " + json1.getJSONObject("sys").getString("country");
                        String description1 = details.getString("description").toUpperCase(Locale.US);
                        //String weatherDescription = details.getString("description");
                        String description = clouds.getString("all") + "%";
                        String temperature = String.format("%.2f", main.getDouble("temp"))+ "°";
                        String humidity = main.getString("humidity") + "%";
                        String pressure = main.getString("pressure") + " hPa";
                        String speed = wind.getString("speed") + "m/s";
                        String updatedOn = df.format(new Date(json1.getLong("dt")*1000));


                        delegate.processFinish(weather_city, description, temperature, humidity, pressure, speed, updatedOn, description1);

                    }
                } catch (JSONException e) {

                }
            }
        }

        public static JSONObject getWeatherJSON(String lat, String lon){
            try {
                URL url = new URL(String.format(OPEN_WEATHER_MAP_URL, lat, lon));
                HttpURLConnection connection =
                        (HttpURLConnection)url.openConnection();

                connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                StringBuffer json1 = new StringBuffer(1024);
                String tmp="";
                while((tmp=reader.readLine())!=null)
                    json1.append(tmp).append("\n");
                reader.close();

                JSONObject data = new JSONObject(json1.toString());

                // This value will be 404 if the request was not successful
                if(data.getInt("cod") != 200){
                    return null;
                }

                return data;
            }catch(Exception e){
                return null;
            }
        }
    }



