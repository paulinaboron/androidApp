package com.example.paulinaapp01.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;

import com.example.paulinaapp01.Adapters.Adapter;
import com.example.paulinaapp01.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Log.d("xxx", "GALLERY");

        Bundle bundle = getIntent().getExtras();
        String dirName = bundle.getString("dir");
        Log.d("xxx", dirName);
        File pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
        File dir = new File(pic, "Boron");

        File selectedDir = new File(dir, dirName);
        selectedDir.mkdir();

        ArrayList<File> files = new ArrayList<>(Arrays.asList(selectedDir.listFiles()));
        Log.d("xxx", String.valueOf(files));

        Adapter adapter = new Adapter (
                GalleryActivity.this,
                R.layout.gallery_row,
                files
        );
        ListView lGallery = findViewById(R.id.listGallery);
        lGallery.setAdapter(adapter);
    }
}