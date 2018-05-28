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

        WeatherItemHolder weatherItemHolder = (WeatherItemHolder) holder;

        DateTime current = data.get(position);


        if(Dates.isToday(current.date)){
            weatherItemHolder.weatherLayout.setBackground(context.getResources().getDrawable(R.drawable.background_day_1));
        }else{
            weatherItemHolder.weatherLayout.setBackground(context.getResources().getDrawable(R.drawable.background_day_2));
        }

        Date date = Dates.getDate(current.date);

        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy hh:mm");
        String displayDate = format.format(date);

        weatherItemHolder.textDateTime.setText(displayDate);
        weatherItemHolder.textMain.setText(current.mainHeadline);
        weatherItemHolder.textDescription.setText(current.description);

        Glide.with(context).load("http://openweathermap.org/img/w/" + current.icon + ".png")
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(weatherItemHolder.imageIcon);


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
        TextView textMain;

        public WeatherItemHolder(View itemView)
        {
            super(itemView);

            weatherLayout = (LinearLayout) itemView.findViewById(R.id.weatherLayout);
            textDateTime = (TextView) itemView.findViewById(R.id.textDateTime);
            textMain = (TextView) itemView.findViewById(R.id.textViewMain);
            textDescription = (TextView) itemView.findViewById(R.id.textViewDescription);
            imageIcon = (ImageView) itemView.findViewById(R.id.imageView1);
        }
    }


}
