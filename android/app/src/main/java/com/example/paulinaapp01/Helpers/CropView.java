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
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.nio.file.Paths;

public class CropView extends androidx.appcompat.widget.AppCompatImageView {

    private Paint paint;
    private Path path;


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

    @SuppressLint("DrawAllocation")
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
        canvas.drawArc(200, 200, 500, 500, 30, 300, true, paint);

        float[] points = {45, 56, 89, 456, 123, 876, 812, 723, 65, 12};
        canvas.drawLines(points, paint);


        paint.setColor(Color.GREEN);
        path = new Path();
        path.moveTo(200, 80);
        path.lineTo(400, 100);
        path.lineTo(56, 891);
        path.lineTo(788, 23);
        path.cubicTo(788, 45, 123, 1444, 344, 1677);
        canvas.drawPath(path, paint);

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
