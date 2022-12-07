package com.example.paulinaapp01.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.paulinaapp01.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        Bundle bundle = getIntent().getExtras();
        String owoc = bundle.getString("owoc").toString();
        Log.d("xxx", owoc); // "śliwka"
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            this.finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}