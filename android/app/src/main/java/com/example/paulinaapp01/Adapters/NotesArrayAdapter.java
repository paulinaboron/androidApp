package com.example.paulinaapp01.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.example.paulinaapp01.Activities.NotesActivity;
import com.example.paulinaapp01.Helpers.DatabaseManager;
import com.example.paulinaapp01.Helpers.Note;
import com.example.paulinaapp01.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NotesArrayAdapter extends ArrayAdapter {

    private ArrayList<Note> _list;
    private Context _context;
    private int _resource;
    private DatabaseManager db;

    public NotesArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);

        this._list = objects;
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
        // inflater - klasa konwertująca xml na kod javy
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.note_row, null);
        // convertView = inflater.inflate(_resource, null);
        // szukamy każdego TextView w layoucie

        TextView tv1 = (TextView) convertView.findViewById(R.id.noteId);
        tv1.setText(_list.get(position).getId());

        TextView tv2 = (TextView) convertView.findViewById(R.id.noteTitle);
        tv2.setText(_list.get(position).getTitle());
        tv2.setTextColor(_list.get(position).getColor());

        TextView tv3 = (TextView) convertView.findViewById(R.id.noteText);
        tv3.setText(_list.get(position).getText());

        View editView = View.inflate(getContext(), R.layout.note_inputs, null);
        LinearLayout lEdit = (LinearLayout) convertView.findViewById(R.id.editField);
        LinearLayout lNote = (LinearLayout) convertView.findViewById(R.id.noteField);
        LinearLayout inputs = (LinearLayout) convertView.findViewById(R.id.editInputs);
        inputs.addView(editView);

        EditText et1 = (EditText) editView.findViewById(R.id.noteTitle);
        EditText et2 = (EditText) editView.findViewById(R.id.noteText);
        et1.setText(_list.get(position).getTitle());
        et1.setTextColor(_list.get(position).getColor());
        et2.setText(_list.get(position).getText());

        Button bAnuluj = (Button) convertView.findViewById(R.id.anulujBtn);
        Button bSave = (Button) convertView.findViewById(R.id.zapiszBtn);

        bAnuluj.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                lEdit.setVisibility(View.GONE);
                lNote.setVisibility(View.VISIBLE);
            }
        });

        bSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String title = String.valueOf(et1.getText());
                String text = String.valueOf(et2.getText());
                int color = et1.getCurrentTextColor();

                db.update(_list.get(position).getId(), title, text, color);
                _list.add(new Note(_list.get(position).getId(), title, text, color));
                _list.remove(position);
                notifyDataSetChanged();
            }
        });




        String [] colors = {"#ff0000", "#00ff00", "#0000ff","#ffff00", "#00ffff", "#ff00ff"};
        LinearLayout lColors = (LinearLayout) convertView.findViewById(R.id.colors);
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
            });

            lColors.addView(b);
        }


        // gdybyśmy chcieli klikać Imageview wewnątrz wiersza:
        ImageView iv1 = (ImageView) convertView.findViewById(R.id.noteImg);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("xxx", "Klik");
            }
        });

        convertView.setOnLongClickListener(new LinearLayout.OnLongClickListener(){
                    @Override
                    public boolean onLongClick(View view) {
                        Log.d("xxx","Dlugie klikanie");


                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setTitle("Edycja");
                        String[] opcje = {"edytuj","usuń","sortuj wg tytułu", "surtuj wg koloru"};
                        alert.setItems(opcje, new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("xxx", String.valueOf(which));
                                switch (which){
                                    case 0:
                                        lEdit.setVisibility(View.VISIBLE);
                                        lNote.setVisibility(View.GONE);
                                        break;
                                    case 1:
                                        db.delete(_list.get(position).getId());
                                        _list.remove(position);
                                        notifyDataSetChanged();
                                        break;
                                    case 2:
                                        Collections.sort(_list, new Comparator<Note>() {
                                            @Override
                                            public int compare(Note a, Note b) {
                                                return a.getTitle().compareTo(b.getTitle());
                                            }
                                        });
                                        notifyDataSetChanged();
                                        break;
                                    case 3:
                                        _list.sort(Comparator.comparing(Note::getColor));
                                        notifyDataSetChanged();
                                        break;
                                }

                            }
                        });
                        alert.show();
                        return false;
                    }
                });

        return convertView;
    }
}
