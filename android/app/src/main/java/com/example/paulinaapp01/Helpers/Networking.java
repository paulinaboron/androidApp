package com.example.paulinaapp01.Helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.bikomobile.multipart.Multipart;
import com.example.paulinaapp01.Activities.SelectedPhotoActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Networking {
    public Networking() {
    }

    public void upload(Context context){
        String path = SelectedPhotoActivity.path;
        Log.d("xxx", "upload " + path);
        Bitmap bmp = BitmapFactory.decodeFile(path);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (preferences.getString("ip", null) != null)
            Log.d("xxx", preferences.getString("ip", null));
        String ip = preferences.getString("ip", null);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Multipart multipart = new Multipart(context);
        multipart.addFile("image/jpeg", "file", "plik.jpg", byteArray);
        multipart.launchRequest("http://" + ip + ":3000/upload",
                response -> {
                    Log.d("xxx", "success");
                    Toast.makeText(context, "Uploaded",Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Log.d("xxx", error.toString());
                    Toast.makeText(context, "Error",Toast.LENGTH_SHORT).show();
                });
    }

    public void share(Context context){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "testshare.txt");
        FileWriter writer;
        try {
            writer = new FileWriter(file);
            writer.append("any data");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/testshare.txt"));
        context.startActivity(Intent.createChooser(share, "Podziel się plikiem!"));
    }

    public boolean connect(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfoWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Log.d("xxx", "isInternet: " + netInfoWifi);

        return netInfoWifi != null && netInfoWifi.isConnectedOrConnecting();
    }


}
