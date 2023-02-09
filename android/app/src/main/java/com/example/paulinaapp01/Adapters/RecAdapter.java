package com.example.paulinaapp01.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paulinaapp01.Activities.MainActivity;
import com.example.paulinaapp01.Helpers.Item;
import com.example.paulinaapp01.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {
    private List<Item> list;
    private Context context;

    public RecAdapter(List<Item> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = preferences.getString("ip",null);

        Item listItem = list.get(position);

        String url = "http://"+ip+":3000/photo/?imgName="+listItem.getName();
        Log.d("xxx", url);
        Picasso
                .get()
                .load(url)
                .into(holder.img);
        holder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        holder.aTxt.setText(" Time: " + listItem.getTime());
        holder.bTxt.setText(" Size: " + listItem.getSize());
    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        private TextView aTxt;
        private TextView bTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            aTxt = itemView.findViewById(R.id.aTxt);
            bTxt = itemView.findViewById(R.id.bTxt);
            img = itemView.findViewById(R.id.recImage);
        }
    }
}
