package com.example.paulinaapp01.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.paulinaapp01.Adapters.RecAdapter;
import com.example.paulinaapp01.Helpers.Item;
import com.example.paulinaapp01.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private LinearLayout lCamera;
    private LinearLayout lAlbums;
    private LinearLayout lNewAlbums;
    private LinearLayout lNotes;
    private LinearLayout lCollages;
    private LinearLayout lNetwork;

    private ArrayList<Item> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("xxx", "MAIN");

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 100);

        lCamera = findViewById(R.id.layoutCamera);
        lCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Wybierz");
                String[] opcje = {"aparat", "galeria"};
                alert.setItems(opcje, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("xxx", String.valueOf(which));

                        if (which == 0) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(intent, 200); // 200 - stała wartość, która później posłuży do identyfikacji tej akcji
                            }
                        } else {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent, 100); // 100 - stała wartość, która później posłuży do identyfikacji tej akcji

                        }
                    }
                });

                alert.show();
            }
        });

        lAlbums = findViewById(R.id.layoutAlbums);
        lAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("xxx", "Click albums");
                Intent intent = new Intent(MainActivity.this, AlbumsActivity.class);
                startActivity(intent);
            }
        });

        lNewAlbums = findViewById(R.id.layoutNewAlbums);
        lNewAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("xxx", "Click new albums");
                Intent intent = new Intent(MainActivity.this, NewAlbumsActivity.class);
                startActivity(intent);
            }
        });

        lNotes = findViewById(R.id.layoutNotes);
        lNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("xxx", "Click notes");
                Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });

        lCollages = findViewById(R.id.layoutCollages);
        lCollages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("xxx", "Click collages");
                Intent intent = new Intent(MainActivity.this, CollagesChoiceActivity.class);
                startActivity(intent);
            }
        });

        lNetwork = findViewById(R.id.lNetwork);
        lNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("xxx", "Click network");
                Intent intent = new Intent(MainActivity.this, NetworkActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String ip = preferences.getString("ip", null);

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest jsonRequest = new JsonArrayRequest(
                Request.Method.GET,
                "http://192.168.1.20:3000/photosJson",
                null,
                response -> {
                    Log.d("xxx", "response: " + response);

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject responseObj = null;
                        try {
                            responseObj = response.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Item listItem = null;
                        try {
                            listItem = new Item(
                                    responseObj.getString("name"),
                                    responseObj.getString("creationTime"),
                                    responseObj.getString("size"),
                                    responseObj.getString("url")
                            );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        list.add(listItem);
                    }
                    Log.d("xxx", list.toString());
                    adapter = new RecAdapter(list, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                },
                error -> {
                    Log.d("xxx", "error" + error.getMessage());
                }
        );

        requestQueue.add(jsonRequest);

    }


    public void checkPermission(String permission, int requestCode) {
        // jeśli nie jest przyznane to zażądaj
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("xxx", "Tak");
                } else {
                    Log.d("xxx", "Nie");
                }
                break;
            case 101:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // tutaj są dostępne dane zdjęcia z aparatu, można je konwertować i zapisywać do pliku
        File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File dir = new File(pic, "Boron");
        Log.d("xxx", Arrays.toString(dir.listFiles()));
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Uwaga!");

        ArrayList<String> opcjeList = new ArrayList<>();
        for (File file : dir.listFiles()) {
            opcjeList.add(file.getName());
        }
        String[] opcje = opcjeList.toArray(new String[opcjeList.size()]);
        Log.d("xxx", Arrays.deepToString(opcje));

        alert.setItems(opcje, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.d("xxx", String.valueOf(which));
                Log.d("xxx", opcje[which]);


                Bitmap bitmap = null;
                if (requestCode == 200) {
                    if (resultCode == RESULT_OK) {
                        Bundle extras = data.getExtras();
                        bitmap = (Bitmap) extras.get("data");
                    }
                } else if (requestCode == 100) {
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
                byte[] byteArray = stream.toByteArray();

                FileOutputStream fs = null;
                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String d = df.format(new Date());
                    String path = dir.getPath() + "/" + opcje[which] + "/" + d + ".jpg";
                    fs = new FileOutputStream(path);
                    fs.write(byteArray);
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        alert.show();

        Log.d("xxx", "200");


    }
}