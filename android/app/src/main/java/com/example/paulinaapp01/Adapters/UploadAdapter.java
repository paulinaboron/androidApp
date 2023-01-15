package com.example.paulinaapp01.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.paulinaapp01.R;

import java.util.ArrayList;
import java.util.List;

public class UploadAdapter extends ArrayAdapter {

    private ArrayList<String> _list;
    private Context _context;
    private int _resource;
    private ArrayList<Integer> _icons;

    public UploadAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);

        this._list= objects;
        this._context = context;
        this._resource = resource;
        this._icons = new ArrayList<>();
        _icons.add(R.drawable.upload);
        _icons.add(R.drawable.share);
        _icons.add(R.drawable.crop);
    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(_resource, null);

        ImageView iv = (ImageView) convertView.findViewById(R.id.uploadIcon);
        iv.setImageResource(_icons.get(position));
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("XXX","klik w obrazek");
            }
        });

        TextView tv = (TextView) convertView.findViewById(R.id.uploadText);
        tv.setText(_list.get(position));

        return convertView;
    }

    @Override
    public int getCount() {
        return _list.size();
    }
}
