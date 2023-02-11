package com.example.paulinaapp01.Helpers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import java.util.ArrayList;

public class Imaging {
    public static Bitmap bitmap;
    private ArrayList<Effect> effectsList;

    public Imaging(Bitmap bitmap) {
        this.bitmap = bitmap;

        float[] normal_tab = {
                1.0f, 0, 0, 0, 0,
                0, 1.0f, 0, 0, 0,
                0, 0, 1.0f, 0, 0,
                0, 0, 0, 1.0f, 0
        };
        float[] red_tab = {
                2, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 1, 0
        };
        float[] neg_tab = {
                -1, 0, 0, 1, 0,
                0, -1, 0, 1, 0,
                0, 0, -1, 1, 0,
                0, 0, 0, 1, 0

        };
        float[] bw_tab = {
                0, 1, 0, 0, 0,
                0, 1, 0, 1, 0,
                0, 1, 0, 0, 0,
                0, 1, 0, 1, 0

        };
        float[] purple_tab = {
                1, -0.2f, 0, 0, 0,
                0, 1, 0, -0.1f, 0,
                0, 1.2f, 1, 0.1f, 0,
                0, 0, 1.7f, 1, 0

        };


        effectsList = new ArrayList<>();
        effectsList.add(new Effect(normal_tab, "Normal"));
        effectsList.add(new Effect(neg_tab, "Negatyw"));
        effectsList.add(new Effect(bw_tab, "B&W"));
        effectsList.add(new Effect(red_tab, "Red"));
        effectsList.add(new Effect(purple_tab, "Purple"));
    }

    public Bitmap changeSomething(float[] tab) {

        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(tab);

        Paint paint = new Paint();
        Bitmap b = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
        Canvas canvas = new Canvas(b);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return b;
    }

    public Bitmap changeSaturation(float value){
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.setSaturation(value);
        Paint paint = new Paint();
        Bitmap b = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
        Canvas canvas = new Canvas(b);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return b;
    }

    public ArrayList<Effect> getEffectsList() {
        return effectsList;
    }
}
