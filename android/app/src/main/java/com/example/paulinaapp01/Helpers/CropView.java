package com.example.paulinaapp01.Helpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.nio.file.Path;

public class CropView extends androidx.appcompat.widget.AppCompatImageView {

    private Paint paint;

    public CropView(@NonNull Context context) {
        super(context);
        this.setBackgroundColor(Color.RED);
        this.setLayoutParams(new RelativeLayout.LayoutParams(800, 2000));
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);
        paint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(20, 20, 200, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);
        canvas.drawLine(200, 30, 600, 600, paint);

        canvas.drawRect(100, 100, 500, 500, paint);
        Rect r = new Rect(120, 120, 520, 520);
        canvas.drawRect(r, paint);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawArc(200, 200, 500, 500, 30, 300, true, paint);

        float[] points = {45, 56, 89, 456, 123, 876, 812, 723, 65, 12};
        canvas.drawLines(points, paint);

//        Path path = new Path();
//        canvas.drawPath();
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
