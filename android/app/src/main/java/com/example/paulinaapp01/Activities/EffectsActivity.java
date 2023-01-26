package com.example.paulinaapp01.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.paulinaapp01.R;

public class EffectsActivity extends AppCompatActivity {

    public String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effects);
        Log.d("xxx", "EFFECTS");

        path = SelectedPhotoActivity.path;


        BitmapFactory.Options options = new BitmapFactory.Options();    //opcje przekształcania bitmapy
        options.inSampleSize = 4; // zmniejszenie jakości bitmapy 4
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        ImageView iv = new ImageView(EffectsActivity.this);
        iv.setImageBitmap(bitmap);                // wstawienie bitmapy do ImageView
        iv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)); //jego wielkość
        RelativeLayout layout = findViewById(R.id.photoEffects);
        layout.addView(iv);
    }
}