package com.example.paulinaapp01.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paulinaapp01.Helpers.Item;
import com.example.paulinaapp01.R;

import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {
    private List<Item> list;

    public RecAdapter(List<Item> list) {
        this.list = list;
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
        Item listItem = list.get(position);
        holder.aTxt.setText(listItem.getA());
        holder.bTxt.setText(listItem.getB());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView aTxt;
        private TextView bTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            aTxt = itemView.findViewById(R.id.aTxt);
            bTxt = itemView.findViewById(R.id.bTxt);
        }
    }
}
