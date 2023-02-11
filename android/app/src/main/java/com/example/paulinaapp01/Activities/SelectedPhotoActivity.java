package com.example.paulinaapp01.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paulinaapp01.Adapters.UploadAdapter;
import com.example.paulinaapp01.Helpers.CropView;
import com.example.paulinaapp01.Helpers.Imaging;
import com.example.paulinaapp01.R;

import java.io.File;
import java.util.ArrayList;

public class SelectedPhotoActivity extends AppCompatActivity {
    public static String path;
    private SeekBar slider;
    private String mode = "";
    private RelativeLayout blackout;
    private TextView blackoutText;
    private Bitmap bitmap;
    private Imaging imaging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_photo);
        Log.d("xxx", "SELECTED");

        slider = findViewById(R.id.slider);
        blackout = findViewById(R.id.blackout);
        blackoutText = findViewById(R.id.blackoutText);

        blackout.animate()
                .alpha(0)
                .withEndAction(()->{})
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(0)
                .start();
        slider.animate()
                .alpha(0)
                .withEndAction(()->{})
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(0)
                .start();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SelectedPhotoActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ip", "192.168.1.20");
        editor.apply();

        ArrayList<String> list = new ArrayList();
        list.add("upload");
        list.add("share");
        list.add("effects");

        UploadAdapter adapter = new UploadAdapter (
                SelectedPhotoActivity.this,
                R.layout.upload_row,
                list
        );
        ListView lv = findViewById(R.id.uploadMenu);
        lv.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        path = bundle.getString("path").toString();
        Log.d("xxx", path);

        BitmapFactory.Options options = new BitmapFactory.Options();    //opcje przekształcania bitmapy
        options.inSampleSize = 4; // zmniejszenie jakości bitmapy 4
        bitmap = BitmapFactory.decodeFile(path, options);
        ImageView iv = findViewById(R.id.selectedImage);
        iv.setImageBitmap(bitmap);                // wstawienie bitmapy do ImageView
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

//        iv.drawRec();

        imaging = new Imaging(bitmap);

        ImageView deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("xxx", "Delete");
                File photo = new File(path);
                photo.delete();
            }
        });

        Button saveIpButton = findViewById(R.id.saveIpButton);
        saveIpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("xxx", "Save IP");
                EditText ipField = findViewById(R.id.ipField);
                String address = String.valueOf(ipField.getText());
                Log.d("xxx", address);
                editor.putString("ip", address);
                editor.apply();
                Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
            }
        });

        ImageView cropIv = findViewById(R.id.cropBtn);
        cropIv.setOnClickListener(v -> {
            Log.d("xxx", "crop");
        });

        ImageView brightIv = findViewById(R.id.brightnessBtn);
        brightIv.setOnClickListener(v -> {
            blackoutText.setText("Brightness");
            mode = "brightness";
            animate();
        });

        ImageView contrastIv = findViewById(R.id.contrastBtn);
        contrastIv.setOnClickListener(v -> {
            blackoutText.setText("Contrast");
            mode = "contrast";
            animate();
        });

        ImageView saturationIv = findViewById(R.id.saturationBtn);
        saturationIv.setOnClickListener(v -> {
            blackoutText.setText("Saturation");
            mode = "saturation";
            animate();
        });

        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mode.equals("brightness")){
                    float brightness = slider.getProgress();
                    float[] brightness_tab = {
                            1, 0, 0, 0, brightness,
                            0, 1, 0, 0, brightness,
                            0, 0, 1, 0, brightness,
                            0, 0, 0, 1, 0 };
                    iv.setImageBitmap(imaging.changeSomething(brightness_tab));
                }
                if(mode.equals("contrast")){
                    float contrast = (float)slider.getProgress() / 100;
                    float[] contrast_tab = {
                            contrast, 0, 0, 0, 0,
                            0, contrast, 0, 0, 0,
                            0, 0, contrast, 0, 0,
                            0, 0, 0, 1, 0 };
                    iv.setImageBitmap(imaging.changeSomething(contrast_tab));
                }
                if(mode.equals("saturation")){
                    float saturation = slider.getProgress();
                    iv.setImageBitmap(imaging.changeSaturation(saturation));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    public void animate(){
        blackout.animate()
                .alpha(1)
                .withEndAction(()->{})
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(0)
                .start();
        blackout.animate()
                .alpha(0)
                .withEndAction(()->{
                    slider.animate()
                            .alpha(1)
                            .withEndAction(()->{})
                            .setInterpolator(new AccelerateDecelerateInterpolator())
                            .setDuration(0)
                            .start();
                })
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(1500)
                .start();
    }
}