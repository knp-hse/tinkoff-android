package com.example.test.hw1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;


public class GraphView extends View {
    private Paint graphPaint;
    private List<PlotItem> items;
    private float height;
    private float width;
    private float minX;
    private float maxX;
    private float minY;
    private float maxY;

    public GraphView(Context context) {
        this(context, null);
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        height = MeasureSpec.getSize(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    public void setItems(List<PlotItem> items) {
        this.items = items;
        calculatePoints();
    }

    public void addItem(PlotItem item) {
        this.items.add(item);
    }

    private void calculatePoints() {
        if (items == null || items.size() == 0)
            return;
        PlotItem item = items.get(0);
        minX = item.minX;
        maxX = item.maxX;
        minY = item.minY;
        maxY = item.maxY;
        for (int i = 1; i < items.size(); i++) {
            item = items.get(i);
            if (item.minX < minX)
                minX = item.minX;
            if (item.maxX > maxX)
                maxX = item.maxX;
            if (item.minY < minY)
                minY = item.minY;
            if (item.maxY > maxY)
                maxY = item.maxY;
        }
        if (minY == maxY)
            maxY = minY + 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float coeffX = width/(maxX - minX);
        float coeffY = height/(maxY - minY);
        float curX;
        float curY;
        float prevX;
        float prevY;

        for (PlotItem it : items) {
            graphPaint.setColor(it.color);

            prevX =  (it.x.get(0) - minX) * coeffX;
            prevY =  height - (it.y.get(0) - minY) * coeffY;
            for (int i = 1; i < it.x.size(); i++) {
                curX  = (it.x.get(i) - minX) * coeffX;
                curY =  height - (it.y.get(i) - minY) * coeffY;
                canvas.drawLine(prevX, prevY, curX, curY, graphPaint);
                prevX = curX;
                prevY =  curY;
            }
        }
    }

    private void init() {
        graphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        graphPaint.setStyle(Paint.Style.STROKE);
        graphPaint.setStrokeWidth(5);
    }

    public float getMinX() {
        return minX;
    }

    public float getMaxX() {
        return maxX;
    }

    public float getMinY() {
        return minY;
    }

    public float getMaxY() {
        return maxY;
    }
}
