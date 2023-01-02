package com.example.paulinaapp01.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

import com.example.paulinaapp01.Helpers.ImageData;
import com.example.paulinaapp01.R;

import java.util.ArrayList;

public class CollagesChoiceActivity extends AppCompatActivity {

    private ArrayList<ImageData> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collages_choice);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Log.d("xxx","szerokość ekranu " + size.x);
        Log.d("xxx","wysokość ekranu " +size.y);
        int halfX = Math.round(size.x/2);
        int halfY = size.y/2 - 50;

        ImageView collage1 = findViewById(R.id.collage1);
        collage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("xxx", "Click collages 1");

                list.clear();
                list.add(new ImageData(0,0, halfX,halfY));
                list.add(new ImageData(halfX,0,halfX,halfY));
                list.add(new ImageData(0,halfY,size.x,halfY));

                goToNextActivity();
            }
        });

        ImageView collage2 = findViewById(R.id.collage2);
        collage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("xxx", "Click collages 2");

                list.clear();
                list.add(new ImageData(0,0,200,100));
                list.add(new ImageData(0,100,100,100));
                list.add(new ImageData(100,100,100,100));

                goToNextActivity();
            }
        });


    }

    public void goToNextActivity(){
        Intent intent = new Intent(CollagesChoiceActivity.this, CollageActivity.class);
        intent.putExtra("list", list);
        startActivity(intent);
    }
}