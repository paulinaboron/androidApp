package com.example.paulinaapp01.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.appcompat.app.AlertDialog;

import com.example.paulinaapp01.Activities.AlbumsActivity;
import com.example.paulinaapp01.Activities.EffectsActivity;
import com.example.paulinaapp01.Activities.SelectedPhotoActivity;
import com.example.paulinaapp01.Helpers.Networking;
import com.example.paulinaapp01.R;

import java.io.File;
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

        TextView tv = (TextView) convertView.findViewById(R.id.uploadText);
        tv.setText(_list.get(position));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("XXX","klik w tekst");
                if(tv.getText() == "upload"){
                    upload();
                }else if(tv.getText() == "share"){
                    share();
                }
                else if(tv.getText() == "effects"){
                    effects();
                }
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return _list.size();
    }

    public void upload(){
        Networking net = new Networking();
        if(net.connect(getContext())){
            net.upload(getContext());
        }
        else{
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("INTERNET");
            alert.setMessage("Brak internetu");
            alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {}});
            alert.show();
        }
    }

    public void share(){
        Networking net = new Networking();
        if(net.connect(getContext())){
            net.share(getContext());
        }
        else{
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("INTERNET");
            alert.setMessage("Brak internetu");
            alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {}});
            alert.show();
        }
    }

    public void effects(){
        Intent intent = new Intent(_context, EffectsActivity.class);
//        intent.putExtra("path", _list.get(position));
        _context.startActivity(intent);
    }


}
