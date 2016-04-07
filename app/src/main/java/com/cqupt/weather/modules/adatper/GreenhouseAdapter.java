package com.cqupt.weather.modules.adatper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqupt.weather.R;
import com.cqupt.weather.bean.historyData;

import java.util.List;

public class GreenhouseAdapter extends RecyclerView.Adapter<GreenhouseAdapter.GreenhouseViewHolder> {
    private Context mContext;
    private List<historyData> historyData;

    public GreenhouseAdapter(Context context, List<historyData> historyData) {
        mContext = context;
        this.historyData = historyData;
    }


    @Override
    public GreenhouseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GreenhouseViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_greenhouse_info, parent, false));
    }


    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final GreenhouseViewHolder holder, final int position) {
        holder.time.setText(historyData.get(position).getTime());
        holder.temperature.setText(mContext
                .getString(R.string.temperature, historyData.get(position).getTemp()));
        holder.humidity.setText(mContext
                .getString(R.string.humidity, historyData.get(position).getHumd()));
    }


    @Override
    public int getItemCount() {
        return historyData.size();
    }

    class GreenhouseViewHolder extends RecyclerView.ViewHolder {
        private TextView time;
        private TextView temperature;
        private TextView humidity;

        public GreenhouseViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            temperature = (TextView) itemView.findViewById(R.id.temperature);
            humidity = (TextView) itemView.findViewById(R.id.humidity);
        }
    }
}
