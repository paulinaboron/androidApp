package com.example.paulinaapp01.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amitshekhar.DebugDB;
import com.example.paulinaapp01.Adapters.NotesArrayAdapter;
import com.example.paulinaapp01.Helpers.DatabaseManager;
import com.example.paulinaapp01.R;

import java.io.File;

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Log.d("xxx", "NOTES");

        DatabaseManager db = new DatabaseManager (
                NotesActivity.this, // activity z galerią zdjęć
                "NotatkiPaulina.db", // nazwa bazy
                null,
                3 //wersja bazy, po zmianie schematu bazy należy ją zwiększyć
        );

        Log.d("xxx", DebugDB.getAddressLog());

        NotesArrayAdapter adapter = new NotesArrayAdapter(
                NotesActivity.this,
                R.layout.note_row,
                db.getAll()
        );

        ListView lNotes = findViewById(R.id.listNotes);
        lNotes.setAdapter(adapter);

    }
}