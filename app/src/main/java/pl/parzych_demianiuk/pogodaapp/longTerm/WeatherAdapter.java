package pl.parzych_demianiuk.pogodaapp.longTerm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pl.parzych_demianiuk.pogodaapp.R;

public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private LayoutInflater inflater;
    ArrayList<DateTime> data;

    public WeatherAdapter(Context context, ArrayList<DateTime> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.weather_item,parent, false);
        WeatherItemHolder holder = new WeatherItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){

        int []icons = {R.drawable.cloud, R.drawable.rainy, R.drawable.snowing, R.drawable.sun,R.drawable.thunderstorm,R.drawable.wind};

        WeatherItemHolder weatherItemHolder = (WeatherItemHolder) holder;

        DateTime current = data.get(position);

        weatherItemHolder.textDateTime.setText(current.date);
        weatherItemHolder.textTemp.setText(current.temp);
        weatherItemHolder.textDescription.setText(current.description);
        weatherItemHolder.cityText.setText(current.city);


        if(current.description.equals("light rain") || current.description.equals("moderate rain") || current.description.equals("heavy intensity rain") || current.description.equals("very heavy rain") || current.description.equals("extreme rain") || current.description.equals("freezing rain") || current.description.equals("light intensity shower rain") || current.description.equals("shower rain") || current.description.equals("heavy intensity shower rain") || current.description.equals("ragged shower rain")){
            weatherItemHolder.imageIcon.setImageResource(icons[1]);
            weatherItemHolder.textDescription.setText("Deszczowo");
        }else if(current.description.equals("clear sky")){
            weatherItemHolder.textDescription.setText("Słonecznie");
            weatherItemHolder.imageIcon.setImageResource(icons[3]);
        }else if(current.description.equals("thunderstorm with light rain") || current.description.equals("thunderstorm with rain") || current.description.equals("thunderstorm with heavy rain") || current.description.equals("light thunderstorm") || current.description.equals("thunderstorm") || current.description.equals("heavy thunderstorm") || current.description.equals("ragged thunderstorm") || current.description.equals("thunderstorm with light drizzle") || current.description.equals("thunderstorm with drizzle") || current.description.equals("thunderstorm with heavy drizzle")){
            weatherItemHolder.textDescription.setText("Burza");
            weatherItemHolder.imageIcon.setImageResource(icons[4]);
        }else if(current.description.equals("few clouds") || current.description.equals("scattered clouds") || current.description.equals("broken clouds") || current.description.equals("overcast clouds") ){
            weatherItemHolder.textDescription.setText("Pochmurnie");
            weatherItemHolder.imageIcon.setImageResource(icons[0]);
        }else if(current.description.equals("light snow") || current.description.equals("snow") || current.description.equals("heavy snow") || current.description.equals("sleet") || current.description.equals("shower sleet") || current.description.equals("light rain and snow") || current.description.equals("rain and snow") || current.description.equals("light shower snow") || current.description.equals("shower snow") || current.description.equals("heavy shower snow")){
            weatherItemHolder.textDescription.setText("Śnieg");
            weatherItemHolder.imageIcon.setImageResource(icons[2]);
        } else { weatherItemHolder.imageIcon.setImageResource(icons[4]);}


//
    }

        @Override
        public int getItemCount(){
        return data.size();
        }

    class WeatherItemHolder extends RecyclerView.ViewHolder
    {
        LinearLayout weatherLayout;
        TextView textDateTime;
        TextView textDescription;
        ImageView imageIcon;
        TextView textTemp;
        TextView cityText;


        public WeatherItemHolder(View itemView)
        {
            super(itemView);

            weatherLayout = (LinearLayout) itemView.findViewById(R.id.weatherLayout);
            textDateTime = (TextView) itemView.findViewById(R.id.textDateTime);
            textDescription = (TextView) itemView.findViewById(R.id.textViewDescription);
            imageIcon = (ImageView) itemView.findViewById(R.id.imageView1);
            textTemp = (TextView)itemView.findViewById(R.id.textTemp);
            cityText = (TextView) itemView.findViewById(R.id.cityText);
        }
    }



}


