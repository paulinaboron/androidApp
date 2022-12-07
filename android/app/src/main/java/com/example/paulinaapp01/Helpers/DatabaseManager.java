package com.example.paulinaapp01.Helpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    public DatabaseManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tabela1 (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'title' TEXT, 'text' TEXT, 'color' INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS tabela1");
        onCreate(db);
    }

    public boolean insert(String title, String text, int color){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("text", text);
        contentValues.put("color", color);

        db.insertOrThrow("tabela1", null, contentValues); // gdy insert się nie powiedzie, będzie błąd
        db.close();
        return true;
    }

    public int delete(String id){
        Log.d("xxx", "delete" + id);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("tabela1",
                "_id = ? ",
                new String[]{id}); // chodzi o id w tej linii
    }

    public void update(String id, String title, String text, int color){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("text", text);
        contentValues.put("color", color);

        db.update("tabela1",
                contentValues,
                "_id = ? ",
                new String[]{id}); // chodzi o id w tej linii
        db.close();
    }


    @SuppressLint("Range")
    public ArrayList<Note> getAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Note> notes = new ArrayList<>();
        Cursor result = db.rawQuery("SELECT * FROM tabela1" , null);
        while(result.moveToNext()){
            notes.add( new Note(
                    result.getString(result.getColumnIndex("_id")),
                    result.getString(result.getColumnIndex("title")),
                    result.getString(result.getColumnIndex("text")),
                    result.getInt(result.getColumnIndex("color"))
            ));

        }
        return notes;
    }
}
