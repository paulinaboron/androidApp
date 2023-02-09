package com.example.paulinaapp01.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.paulinaapp01.Helpers.Effect;
import com.example.paulinaapp01.Helpers.Imaging;
import com.example.paulinaapp01.R;

import org.w3c.dom.Text;

public class EffectsActivity extends AppCompatActivity {

    public String path;
    public Imaging imaging;
    public ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effects);
        Log.d("xxx", "EFFECTS");

        path = SelectedPhotoActivity.path;

        BitmapFactory.Options options = new BitmapFactory.Options();    //opcje przekształcania bitmapy
        options.inSampleSize = 4; // zmniejszenie jakości bitmapy 4
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        imaging = new Imaging((bitmap));

        iv = findViewById(R.id.photoEffects);
        iv.setImageBitmap(bitmap);                // wstawienie bitmapy do ImageView
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);


        Bitmap smallBmp = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        Imaging smallImaging = new Imaging(smallBmp);

        LinearLayout scrollL = findViewById(R.id.scrollLayout);


        for(Effect e : smallImaging.getEffectsList()){
            Bitmap b = smallImaging.changeSomething(e.array);

            ImageView smallIv = new ImageView(EffectsActivity.this);
            smallIv.setImageBitmap(b);
            smallIv.setScaleType(ImageView.ScaleType.CENTER_CROP);

            LinearLayout ll = new LinearLayout(EffectsActivity.this);
            ll.setOrientation(LinearLayout.VERTICAL);
            TextView tx = new TextView(EffectsActivity.this);
            tx.setText(e.name);
            ll.addView(smallIv);
            ll.addView(tx);
            scrollL.addView(ll);

            smallIv.setOnClickListener(v -> {
                Log.d("xxx", e.name);
                changeBitmap(e.array);
            });
        }
    }

    public void changeBitmap(float[] array){
        Bitmap newBitmap = imaging.changeSomething(array);
        iv.setImageBitmap(newBitmap);
    }
}