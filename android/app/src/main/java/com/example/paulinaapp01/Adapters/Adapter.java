package com.example.paulinaapp01.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.paulinaapp01.Activities.PhotosActivity;
import com.example.paulinaapp01.Activities.SelectedPhotoActivity;
import com.example.paulinaapp01.Helpers.DatabaseManager;
import com.example.paulinaapp01.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter {

    private ArrayList<File> _list;
    private Context _context;
    private int _resource;
    private DatabaseManager db;

    public Adapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);

        this._list= (ArrayList<File>) objects;
        this._context = context;
        this._resource = resource;

        this.db = new DatabaseManager (
                getContext(), // activity z galerią zdjęć
                "NotatkiPaulina.db", // nazwa bazy
                null,
                3 //wersja bazy, po zmianie schematu bazy należy ją zwiększyć
        );
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(_resource, null);

        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("XXX","klik w obrazek " + position);
                Intent intent = new Intent(_context, SelectedPhotoActivity.class);
                intent.putExtra("path", _list.get(position));
                _context.startActivity(intent);
            }

        });


        Uri uri = Uri.fromFile(this._list.get(position));
        img.setImageURI(uri);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        ImageView deleteBtn = (ImageView) convertView.findViewById(R.id.deleteButton);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("XXX","klik w obrazek delete " + position);
                displayDelete(position);
            }
        });

        ImageView editBtn = (ImageView) convertView.findViewById(R.id.editButton);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("XXX","klik w obrazek edit " + position);
                displayEdit(String.valueOf(_list.get(position)));
            }
        });

        ImageView infoBtn = (ImageView) convertView.findViewById(R.id.infoButton);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("XXX","klik w obrazek info " + position);
                displayInfo(String.valueOf(_list.get(position)));
            }
        });


        return convertView;
    }

    @Override
    public int getCount() {
        return _list.size();
    }

    public void displayDelete(int i){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Usuwanie");
        alert.setMessage("Czy napewno chcesz usunąć?");

        alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                _list.get(i).delete();
                _list.remove(i);
                notifyDataSetChanged();
            }

        });

        alert.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.d("xxx", "nie");
            }
        });
        alert.show();
    }

    public void displayInfo(String info){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Info");
        alert.setCancelable(false);  //nie zamyka się po kliknięciu poza nim
        alert.setMessage(info);
        alert.setNeutralButton("OK", null).show();  // null to pusty click
    }

    public void displayEdit(String path){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Edycja");
        alert.setMessage("Podaj tytuł i treść notatki. Wybierz kolor tekstu");
        View editView = View.inflate(getContext(), R.layout.note_inputs, null);
        alert.setView(editView);

        String [] colors = {"#ff0000", "#00ff00", "#0000ff","#ffff00", "#00ffff", "#ff00ff"};
        LinearLayout lColors = (LinearLayout) editView.findViewById(R.id.colors);
        for(String c: colors){
            Button b = new Button(getContext());
            b.setBackgroundColor(Color.parseColor(c));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(60,60);
            b.setLayoutParams(params);

            b.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Log.d("xxx", c);
                    EditText et = (EditText) editView.findViewById(R.id.noteTitle);
                    et.setTextColor(Color.parseColor(c));
                }
            }
            );

            lColors.addView(b);
        }

        alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.d("xxx", "Tak");
                EditText et1 = (EditText) editView.findViewById(R.id.noteTitle);
                EditText et2 = (EditText) editView.findViewById(R.id.noteText);
                Log.d("xxx", String.valueOf(et1.getText()));
                Log.d("xxx", String.valueOf(et2.getText()));
                int color = et1.getCurrentTextColor();
                Log.d("xxx", String.valueOf(color));

                db.insert(String.valueOf(et1.getText()), String.valueOf(et2.getText()), color);
            }

        });

        alert.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.d("xxx", "nie");
            }
        });
        alert.show();
    }

}


