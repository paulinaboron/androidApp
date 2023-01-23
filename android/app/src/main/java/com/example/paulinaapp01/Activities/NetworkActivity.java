package com.example.paulinaapp01.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.paulinaapp01.Helpers.CropView;
import com.example.paulinaapp01.R;

public class NetworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        RelativeLayout rl = findViewById(R.id.networkLayout);

        CropView myImg = new CropView(NetworkActivity.this);
        rl.addView(myImg );
        myImg.setX(100); //pozycja względem (0,0) layoutu
        myImg.setY(100);

    }
}