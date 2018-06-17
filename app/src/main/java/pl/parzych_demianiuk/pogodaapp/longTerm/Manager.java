package pl.parzych_demianiuk.pogodaapp.longTerm;


import android.content.Context;
import android.content.DialogInterface;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Display;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Manager extends AppCompatActivity {




    private static  String OPEN_WEATHER_MAP_API = "dd91011a405eba9a769d8e1ed60e2436";

    private static String OPEN_WEATHER_API = "http://api.openweathermap.org/data/2.5/forecast?q=Warszawa,pl&units=metric&mode=json&appid=" + OPEN_WEATHER_MAP_API;



    private static Manager instance;
    private static RequestQueue requestQueue;

    private static Context context;

    private Manager(Context context) {
        Manager.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized Manager getInstance(Context context) {
        if (instance == null) {
            instance = new Manager(context);
        }
        return instance;
    }

    public static RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }








    public static <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public static void GetWeather(final Listener<ArrayList> okListener, final Listener errorListener) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, OPEN_WEATHER_API, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    ArrayList result = parseWeatherObject(response);
                    okListener.onResult(result);
                } catch (JSONException e) {
                    errorListener.onResult(null);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorListener.onResult(null);
            }
        }
        );
        addToRequestQueue(jsObjRequest);
    }


    private static ArrayList parseWeatherObject(JSONObject json)
        throws  JSONException{


        ArrayList arrayList = new ArrayList();

        JSONArray list = json.getJSONArray("list");


        for(int i=0;i<list.length();i++){

            DateTime dateTime = new DateTime();
            JSONObject dtItem = list.getJSONObject(i);

            dateTime.date = dtItem.getString("dt_txt");

            JSONArray weatherArray = dtItem.getJSONArray("weather");
            JSONObject obj = (JSONObject) weatherArray.get(0);

            
            dateTime.description = obj.getString("description");
            dateTime.icon = obj.getString("icon");
            JSONObject mainobj = dtItem.getJSONObject("main");
            dateTime.temp = String.format("%.1f", mainobj.getDouble("temp"))+ "°C";


            arrayList.add(dateTime);
        }
        return arrayList;
    }


}


