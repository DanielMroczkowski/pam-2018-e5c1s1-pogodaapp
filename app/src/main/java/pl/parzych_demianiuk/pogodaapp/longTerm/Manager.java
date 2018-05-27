package pl.parzych_demianiuk.pogodaapp.longTerm;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;

public class Manager {

    private static  String OPEN_WEATHER_MAP_API = "0f40b9a2fd7aced5d29df69a4d963357";

    private static String OPEN_WEATHER_API = "http://api.openweathermap.org/data/2.5/forecast?lat=35&lon=139?&units=metric" + OPEN_WEATHER_MAP_API;



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

            dateTime.mainHeadline = obj.getString("main");
            dateTime.description = obj.getString("description");
            dateTime.icon = obj.getString("icon");

            arrayList.add(dateTime);
        }
        return arrayList;
    }
}


