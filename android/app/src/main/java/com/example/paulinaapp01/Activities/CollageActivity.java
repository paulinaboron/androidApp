package com.example.paulinaapp01.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.paulinaapp01.Helpers.ImageData;
import com.example.paulinaapp01.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CollageActivity extends AppCompatActivity {
    ImageView selectedIv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);

        ArrayList<ImageData> list = (ArrayList<ImageData>) getIntent().getExtras().getSerializable("list");
        Log.d("lista","rozmiar listy 1 "+list.get(1).getX());

        FrameLayout container = findViewById(R.id.container);
        container.setDrawingCacheEnabled(true);


        for(ImageData img : list){
            ImageView iv = new ImageView(CollageActivity.this);
            iv.setX(img.getX());
            iv.setY(img.getY());
            iv.setLayoutParams(new LinearLayout.LayoutParams(img.getWidth(),img.getHeight()));
            iv.setImageResource(R.drawable.baseline_photo_camera_white_36dp);
            iv.setPadding(30, 30, 30, 30);
            iv.setScaleType(ImageView.ScaleType.CENTER);
            iv.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            container.addView(iv);

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedIv = iv;
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, 100);
                }
            });
        }

        ImageView flipButton = findViewById(R.id.flipBtn);
        flipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matrix matrix = new Matrix();
                matrix.postScale(-1.0f, 1.0f);


                Bitmap oryginal = ((BitmapDrawable) selectedIv.getDrawable()).getBitmap();
                Bitmap rotated = Bitmap.createBitmap(oryginal, 0, 0, oryginal.getWidth(), oryginal.getHeight(), matrix, true);

                selectedIv.setImageBitmap(rotated);
            }
        });

        ImageView rotateButton = findViewById(R.id.rotateBtn);
        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);

                Bitmap oryginal = ((BitmapDrawable) selectedIv.getDrawable()).getBitmap();
                Bitmap rotated = Bitmap.createBitmap(oryginal, 0, 0, oryginal.getWidth(), oryginal.getHeight(), matrix, true);

                selectedIv.setImageBitmap(rotated);
            }
        });

        ImageView saveButton = findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("xxx", "save");
                Bitmap bitmap = container.getDrawingCache(true);

                File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                File dir = new File(pic, "Boron");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                FileOutputStream fs = null;
                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String d = df.format(new Date());
                    String path = dir.getPath() + "/" + "collages" + "/" + d + ".jpg";
                    fs = new FileOutputStream(path);
                    fs.write(byteArray);
                    fs.close();
                    Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("xxx", e.getMessage());
                }
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("xxx", String.valueOf(requestCode));
        Log.d("xxx", String.valueOf(resultCode));

        Bitmap bitmap = null;
        if (requestCode == 100) {
            assert data != null;
            Uri imgData = data.getData();
            InputStream inpStream = null;
            try {
                inpStream = getContentResolver().openInputStream(imgData);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap = BitmapFactory.decodeStream(inpStream);
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        selectedIv.setPadding(2, 2, 2, 2);
        selectedIv.setImageBitmap(bitmap);
        selectedIv.setScaleType(ImageView.ScaleType.CENTER_CROP);

    }
}