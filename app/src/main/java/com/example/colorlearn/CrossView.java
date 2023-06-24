package com.example.colorlearn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.camera.view.PreviewView;

public class CrossView extends View {
    private Paint drawPaint;

    public CrossView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int offsetRadius = 5;
        int strokeLength = 100;
        //top line
        canvas.drawLine(getWidth() / 2.0f, getHeight() / 2.0f - strokeLength, getWidth() / 2.0f, getHeight() / 2.0f - drawPaint.getStrokeWidth() - offsetRadius, drawPaint);
        //bottom line
        canvas.drawLine(getWidth() / 2.0f, getHeight() / 2.0f + strokeLength, getWidth() / 2.0f, getHeight() / 2.0f + drawPaint.getStrokeWidth() + offsetRadius, drawPaint);
        //left line
        canvas.drawLine(getWidth() / 2.0f - strokeLength, getHeight() / 2.0f, getWidth() / 2.0f - drawPaint.getStrokeWidth() - offsetRadius, getHeight() / 2.0f, drawPaint);
        //right line
        canvas.drawLine(getWidth() / 2.0f + strokeLength, getHeight() / 2.0f, getWidth() / 2.0f + drawPaint.getStrokeWidth() + offsetRadius, getHeight() / 2.0f, drawPaint);
        //circle in the center
        canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, offsetRadius * 2 + drawPaint.getStrokeWidth() / 2.0f, drawPaint);
    }

    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(Color.WHITE);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(7);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }
}
