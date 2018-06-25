package pl.parzych_demianiuk.pogodaapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;



public class Weather {

    /**
     * zmienna przechowująca link do openweather.org dla funkcji lokalizującej po współrzędnych urządzenia
     */
    private static final String OPEN_WEATHER_MAP_URL =
            "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric";
    /**
     * zmienna przechowująca link do openweather.org dla funkcji lokalizującej po wyszukiwanym mieście
     */
    private static final String OPEN_WEATHER_MAP_URL_CITY =
            "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    /**
     * API wykorzystywane do pobierania pogody z openweathermap.org
     */
    private static final String OPEN_WEATHER_MAP_API = "ac1d416e05fef7cb178f8d3aa90fa2c3";


    public interface AsyncResponse {

        void processFinish(String output1, String output2, String output3, String output4, String output5, String output6, String output7, String output8);
    }

    public static class placeIdTask extends AsyncTask<String, Void, JSONObject> {

        public AsyncResponse delegate = null;


        public placeIdTask(AsyncResponse asyncResponse) {
            delegate = asyncResponse;

        }
        /**
         * funkcja odpowiedzialna za pobranie obiektu JSON wykorzystując getWeatherCityJSON i jest wykonywana w "tle"
         * @param params współrzędne geograficzne
         * @return
         */
        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject jsonWeather = null;
            try {
                jsonWeather = getWeatherJSON(params[0], params[1]);
            } catch (Exception e) {

            }


            return jsonWeather;
        }
        /**
         * funkcja odpowiedzialna za pobranie składnika pogody ze strony openweathermap.org np. temperatura, wilgotność
         * @param json obiekt, który jest pobrany z funkcji getWeathterJSON
         */
        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if(json != null){
                    JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    //JSONObject weather = json.getJSONObject("weather");
                    JSONObject main = json.getJSONObject("main");
                    JSONObject wind = json.getJSONObject("wind");
                    JSONObject clouds = json.getJSONObject("clouds");
                    DateFormat df = DateFormat.getDateTimeInstance();


                    String city = json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country");
                    String description1 = details.getString("description").toUpperCase(Locale.US);
                    //String weatherDescription = details.getString("description");
                    String description = clouds.getString("all") + "%";
                    String temperature = String.format("%.1f", main.getDouble("temp"))+ "&deg;";
                    String humidity = main.getString("humidity") + "%";
                    String pressure = main.getString("pressure") + " hPa";
                    String speed = wind.getString("speed") + "m/s";
                    String updatedOn = df.format(new Date(json.getLong("dt")*1000));


                    delegate.processFinish(city, description, temperature, humidity, pressure, speed, updatedOn, description1);

                }
            } catch (JSONException e) {

            }



        }
    }

    public static class placeIdTaskCity extends AsyncTask<String, Void, JSONObject> {

        public AsyncResponse delegate = null;


        public placeIdTaskCity(AsyncResponse asyncResponse) {
            delegate = asyncResponse;

        }

        /**
         * funkcja odpowiedzialna za pobranie obiektu JSON wykorzystując getWeatherCityJSON i jest wykonywana w "tle"
         * @param params wyszukiwane miasto
         * @return
         */
        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject jsonWeather = null;
            try {
                jsonWeather = getWeatherCityJSON(params[0]);
            } catch (Exception e) {

            }


            return jsonWeather;
        }

        /**
         * funkcja odpowiedzialna za pobranie składnika pogody ze strony openweathermap.org np. temperatura, wilgotność
         * @param json obiekt, który jest pobrany z funkcji getWeathterJSON
         */
        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if(json != null){
                    JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    //JSONObject weather = json.getJSONObject("weather");
                    JSONObject main = json.getJSONObject("main");
                    JSONObject wind = json.getJSONObject("wind");
                    JSONObject clouds = json.getJSONObject("clouds");
                    DateFormat df = DateFormat.getDateTimeInstance();


                    String city = json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country");
                    String description1 = details.getString("description").toUpperCase(Locale.US);
                    //String weatherDescription = details.getString("description");
                    String description = clouds.getString("all") + "%";
                    String temperature = String.format("%.1f", main.getDouble("temp"))+ "&deg;";
                    String humidity = main.getString("humidity") + "%";
                    String pressure = main.getString("pressure") + " hPa";
                    String speed = wind.getString("speed") + "m/s";
                    String updatedOn = df.format(new Date(json.getLong("dt")*1000));


                    delegate.processFinish(city, description, temperature, humidity, pressure, speed, updatedOn, description1);

                }
            } catch (JSONException e) {

            }



        }
    }

    /**
     * funkcja odpowiedzialna za nawiązanie połączenia ze stroną openweathermap.org, wykorzystanie API do pobrania pogody przez lokalizację oraz pobranie obiektu JSON
     * @param lat lattitude - szerokość geograficzna
     * @param lon longitude - długość geograficzna
     * @return
     */
    public static JSONObject getWeatherJSON(String lat, String lon){
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_URL, lat, lon));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not successful
            if(data.getInt("cod") != 200){
                return null;
            }

            return data;
        }catch(Exception e){
            return null;
        }
    }

    /**
     * funkcja odpowiedzialna za nawiązanie połączenia ze stroną openweathermap.org i wykorzystanie API do pobrania pogody przez miasto
     * @param city wyszukiwane miasto
     * @return
     */
    public static JSONObject getWeatherCityJSON(String city){
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_URL_CITY, city));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

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