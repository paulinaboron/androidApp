package com.example.paulinaapp01.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paulinaapp01.Adapters.UploadAdapter;
import com.example.paulinaapp01.R;

import java.io.File;
import java.util.ArrayList;

public class SelectedPhotoActivity extends AppCompatActivity {
    public static String path;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_photo);
        Log.d("xxx", "SELECTED");

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SelectedPhotoActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ip", "192.168.119.108");
        editor.apply();

        ArrayList<String> list = new ArrayList();
        list.add("upload");
        list.add("share");
        list.add("crop");

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
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        ImageView iv = new ImageView(SelectedPhotoActivity.this);
        iv.setImageBitmap(bitmap);                // wstawienie bitmapy do ImageView
        iv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)); //jego wielkość
        LinearLayout layout = findViewById(R.id.layoutPhoto);
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

        Button saveIpButton = findViewById(R.id.saveIpButton);
        saveIpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("xxx", "Save IP");
                EditText ipField = findViewById(R.id.ipField);
                String address = String.valueOf(ipField.getText());
                Log.d("xxx", address);
                editor.putString("ip", "192.168.119.108");
                editor.apply();
                Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
            }
        });

//        pDialog = new ProgressDialog(this);
//        pDialog.setMessage("ładowanie");
//        pDialog.setCancelable(false); // nie da się zamknąć klikając w ekran
//        pDialog.show();
    }
}