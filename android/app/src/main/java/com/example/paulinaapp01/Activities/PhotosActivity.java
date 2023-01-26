package com.example.paulinaapp01.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.paulinaapp01.R;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class PhotosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        Log.d("xxx", "PHOTOS");

        Bundle bundle = getIntent().getExtras();
        String dirName = bundle.getString("dir");
        Log.d("xxx", dirName);

        File pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
        File dir = new File(pic, "Boron");

        File selectedDir = new File(dir, dirName);
        selectedDir.mkdir();

        Log.d("xxx", Arrays.toString(selectedDir.listFiles()));

        LinearLayout layout = findViewById(R.id.layoutPhotos);

        for (File file : Objects.requireNonNull(selectedDir.listFiles())) {
            //jeśli File jest plikiem
            if(file.isFile()){
                ImageView iv = new ImageView(PhotosActivity.this);
                String imagepath = file.getPath();                             // pobierz scieżkę z obiektu File
                Bitmap bmp = betterImageDecode(imagepath);    // własna funkcja betterImageDecode opisana jest poniżej
                iv.setImageBitmap(bmp);                // wstawienie bitmapy do ImageView
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        500
                );
                params.setMargins(20, 10, 20, 10);

                iv.setLayoutParams(params); //jego wielkość
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                layout.addView(iv);

                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("xxx", "Click photo" + imagepath);
                        Intent intent = new Intent(PhotosActivity.this, SelectedPhotoActivity.class);
                        intent.putExtra("path", imagepath);
                        startActivity(intent);
                    }
                });
            }

        }


//        Bitmap b = (Bitmap) selectedDir.listFiles()[0];
//        img.setImageBitmap(b);
//        LinearLayout layout = findViewById(R.id.layoutPhotos);
//        layout.addView(img);
    }

    private Bitmap betterImageDecode(String filePath) {
        Bitmap myBitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();    //opcje przekształcania bitmapy
        options.inSampleSize = 4; // zmniejszenie jakości bitmapy 4x
        //
        myBitmap = BitmapFactory.decodeFile(filePath, options);
        return myBitmap;
    }
}