package com.example.paulinaapp01.Helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.nio.file.Paths;

public class CropView extends androidx.appcompat.widget.AppCompatImageView {

    private Paint paint;


    public CropView(@NonNull Context context) {
        super(context);
        this.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.GREEN);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, 600, 600, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("xxx", "DOWN -> pozycja: " + event.getX() + "-" + event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("xxx", "MOVE -> pozycja: " + event.getX() + "-" + event.getY());
                //odrysowanie canvasa
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Log.d("xxx", "UP -> pozycja: " + event.getX() + "-" + event.getY());
                break;
        }
        return true;
    }

    public void repaintAll(){
        invalidate();
    }
}
