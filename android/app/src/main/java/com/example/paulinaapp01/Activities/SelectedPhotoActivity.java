package com.example.paulinaapp01.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.paulinaapp01.R;

import java.io.File;

public class SelectedPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_photo);
        Log.d("xxx", "SELECTED");


        Bundle bundle = getIntent().getExtras();
        String path = bundle.getString("path").toString();
        Log.d("xxx", path);


        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();    //opcje przekształcania bitmapy
        options.inSampleSize = 4; // zmniejszenie jakości bitmapy 4
        bitmap = BitmapFactory.decodeFile(path, options);
        ImageView iv = new ImageView(SelectedPhotoActivity.this);
        iv.setImageBitmap(bitmap);                // wstawienie bitmapy do ImageView
        iv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)); //jego wielkość
        RelativeLayout layout = findViewById(R.id.layoutPhoto);
        layout.addView(iv);

        iv.setOnClickListener(new View.OnClickListener() {
            boolean helper = true;

            @Override
            public void onClick(View view) {
                Log.d("xxx", "Click photo");
                if(helper){
                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }else{
                    iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
                helper = !helper;
            }
        });


        ImageView deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("xxx", "Delete");
                File photo = new File(path);
                photo.delete();
            }
        });
    }
}