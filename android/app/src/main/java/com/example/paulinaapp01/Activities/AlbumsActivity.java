package com.example.paulinaapp01.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.paulinaapp01.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class AlbumsActivity extends AppCompatActivity {

    private ListView listAlbums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        Log.d("xxx", "ALBUMS");

        File pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
        File dir = new File(pic, "Boron");
        dir.mkdir();

        File dirPeople = new File(dir, "ludzie");
        dirPeople.mkdir();
        Log.d("xxx", dirPeople.getPath() );
        File dirPlaces = new File(dir, "miejsca");
        dirPlaces.mkdir();
        File dirThings = new File(dir, "rzeczy");
        dirThings.mkdir();

        File[] files = dir.listFiles(); // tablica plików
        refreshDirs(dir);


        listAlbums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("xxx","numer klikanego wiersza w ListView = " + i);

                Intent intent = new Intent(AlbumsActivity.this, PhotosActivity.class);
                intent.putExtra("dir", files[i].getName());
                startActivity(intent);
            }
        });

        listAlbums.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("xxx","Dlugie klikanie nr = " + i);

                AlertDialog.Builder alert = new AlertDialog.Builder(AlbumsActivity.this);
                alert.setTitle("Uwaga!");
                alert.setMessage("Czy napewno chcesz usunąć ten folder?");
                alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("xxx", String.valueOf(which));

                        for (File file : files[i].listFiles()){
                            file.delete();
                        }
                        files[i].delete();

                        refreshDirs(dir);
                    }
                });

                alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("xxx", String.valueOf(which));
                    }
                });
                alert.show();

                return false;
            }
        });

        ImageView newDirButton = findViewById(R.id.newDirButton);
        newDirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("xxx", "Click new dir");

                AlertDialog.Builder alert = new AlertDialog.Builder(AlbumsActivity.this);
                alert.setTitle("Nowy folder");
                alert.setMessage("Podaj nazwę");
                EditText input = new EditText(AlbumsActivity.this);
                input.setText("folder001");
                alert.setView(input);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("xxx", String.valueOf(input.getText()));
                        File newDir = new File(dir, String.valueOf(input.getText()));
                        newDir.mkdir();

                        refreshDirs(dir);
                    }
                });

                alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("xxx", String.valueOf(which));
                    }
                });
                alert.show();
            }
        });
    }

    protected void refreshDirs(File dir){
        File[] files = dir.listFiles(); // tablica plików
        Log.d("xxx", Arrays.deepToString(files));
        ArrayList<String> fileNames = new ArrayList<>();
        assert files != null;
        for (File file : files){
            fileNames.add(file.getName());
        }

        listAlbums = findViewById(R.id.listAlbums);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                AlbumsActivity.this,       // tzw Context
                R.layout.row,            // nazwa pliku xml naszego wiersza na liście
                R.id.tv1,                // id pola txt w wierszu
                fileNames );                 // tablica przechowująca testowe dane

        listAlbums.setAdapter(adapter);
    }

}