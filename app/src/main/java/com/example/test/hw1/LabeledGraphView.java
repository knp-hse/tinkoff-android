package com.example.test.hw1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewGroup;

import java.util.List;


public class LabeledGraphView extends ViewGroup {
    private float xLabelHeight;
    private float yLabelHeight;
    private float coordHeight;
    private float coordWidth;
    private float coordLineLength;
    private float xOffset;
    private float yOffset;
    private float restOffset;
    private boolean showGrid;

    private String xLabel = "x";
    private String yLabel = "y";
    private Paint textPaint;
    private Paint gridPaint;
    private GraphView mGraphView;
    private RectF graphBounds;
    private float minX;
    private float maxX;
    private float minY;
    private float maxY;
    private float gridFirstX;
    private float gridFirstY;
    private float gridStepX = 0.0f;
    private float gridStepY = 0.0f;



    public LabeledGraphView(Context context) {
        super(context);
    }

    public LabeledGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LabeledGraphView,
                0, 0
        );
        try {
            xLabelHeight = a.getDimension(R.styleable.LabeledGraphView_xLabelHeight, 40.0f);
            yLabelHeight = a.getDimension(R.styleable.LabeledGraphView_yLabelHeight, 40.0f);
            coordHeight = a.getDimension(R.styleable.LabeledGraphView_coordHeight, 40.0f);
            coordWidth = a.getDimension(R.styleable.LabeledGraphView_coordWidth, 100.0f);
            coordLineLength = a.getDimension(R.styleable.LabeledGraphView_coordLineLength, 15.0f);
            xOffset = a.getDimension(R.styleable.LabeledGraphView_xOffset, 20.0f);
            yOffset = a.getDimension(R.styleable.LabeledGraphView_yOffset, 20.0f);
            restOffset = a.getDimension(R.styleable.LabeledGraphView_restOffset, 20.0f);
            showGrid = a.getBoolean(R.styleable.LabeledGraphView_showGrid, false);
        } finally {
            a.recycle();
        }
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(xLabelHeight);
        canvas.drawText(xLabel, (graphBounds.centerX()),
                graphBounds.bottom + yOffset + 2 * restOffset + coordHeight + coordLineLength + xLabelHeight,
                textPaint);

        textPaint.setTextSize(yLabelHeight);
        float x = yLabelHeight;
        float y = graphBounds.centerY();
        canvas.translate(x, y);
        canvas.rotate(-90);
        canvas.drawText(yLabel, 0, 0, textPaint);
        canvas.rotate(90);
        canvas.translate(-x, -y);

        gridPaint.setStrokeWidth(3);
        canvas.drawRect(graphBounds.left - xOffset, 3.0f, graphBounds.right + xOffset - 3,
                graphBounds.bottom + yOffset, gridPaint);

        textPaint.setTextSize(coordHeight);
        for (float f = gridFirstX; f <= maxX;  f += gridStepX) {
            if (showGrid)
                gridPaint.setStrokeWidth(3);
            x = graphBounds.left + (f - minX) * graphBounds.width() / (maxX - minX);
            canvas.drawLine(x, graphBounds.bottom + yOffset,
                    x, graphBounds.bottom + yOffset + coordLineLength, gridPaint);
            canvas.drawText(Float.toString(f), x,
                    graphBounds.bottom + yOffset + coordLineLength + restOffset + xLabelHeight,  textPaint);

            if (showGrid){
                gridPaint.setStrokeWidth(1);
                canvas.drawLine(x, graphBounds.bottom + yOffset, x, 0, gridPaint);
            }
        }
        textPaint.setTextAlign(Paint.Align.RIGHT);
        for (float f = gridFirstY; f <= maxY;  f += gridStepY) {
            if (showGrid)
                gridPaint.setStrokeWidth(3);
            y = graphBounds.bottom - (f -  minY) * graphBounds.height() / (maxY - minY);
            canvas.drawLine(yLabelHeight + 2 * restOffset + coordWidth , y,
                    yLabelHeight + 2 * restOffset + coordWidth + coordLineLength, y, gridPaint);
            canvas.drawText(Float.toString(f), yLabelHeight + restOffset + coordWidth,
                    y + coordHeight / 2,  textPaint);

            if (showGrid) {
                gridPaint.setStrokeWidth(1);
                canvas.drawLine(graphBounds.left -  xOffset , y,
                        graphBounds.right + xOffset, y, gridPaint);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float padX = yLabelHeight + 2 * restOffset + coordLineLength + coordWidth + xOffset;
        float padY = xLabelHeight + 2 * restOffset + coordLineLength + coordHeight + yOffset;
        graphBounds = new RectF(padX,  yOffset + 3, w - xOffset - 3, h - padY);
        mGraphView.layout((int) graphBounds.left, (int) graphBounds.top,
                (int) graphBounds.right, (int) graphBounds.bottom);
        mGraphView.measure(
                MeasureSpec.makeMeasureSpec((int) (graphBounds.right - graphBounds.left), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec((int) (graphBounds.bottom - graphBounds.top), MeasureSpec.EXACTLY));
    }

    public void setLabels(String xLabel, String yLabel) {
        this.xLabel = xLabel;
        this.yLabel = yLabel;
    }

    public void setGrid(float firstX, float stepX, float firstY, float stepY) {
        gridFirstX = firstX;
        gridFirstY = firstY;
        gridStepX = stepX;
        gridStepY = stepY;
    }

    private void init(){
        setWillNotDraw(false);

        mGraphView = new GraphView(getContext());
        addView(mGraphView);

        textPaint = new Paint();
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStyle(Paint.Style.FILL);

        gridPaint = new Paint();
        gridPaint.setStyle(Paint.Style.STROKE);
    }

    public void setItems(List<PlotItem> items) {
        mGraphView.setItems(items);
        updateGrid();
    }

    public void addItem(PlotItem item)  {
        mGraphView.addItem(item);
        updateGrid();
    }

    private void updateGrid() {
        minX = mGraphView.getMinX();
        minY = mGraphView.getMinY();
        maxX = mGraphView.getMaxX();
        maxY = mGraphView.getMaxY();
        if (gridStepX == 0) {
            gridFirstX = minX;
            gridStepX = (maxX - minX) / 10;
        } else if (gridFirstX < minX) {
            int steps = (int) ((minX - gridFirstX) / gridStepX);
            if (steps < (minX - gridFirstX) / gridStepX)
                steps++;
            gridFirstX += steps * gridStepX;
        }
        if (gridStepY == 0) {
            gridFirstY = minY;
            gridStepY = (maxY - minY) / 10;
        } else if (gridFirstY < minY) {
            int steps = (int) ((minY - gridFirstY) / gridStepY);
            if (steps < (minY - gridFirstY) / gridStepY)
                steps++;
            gridFirstY += steps * gridStepY;
        }
    }


}
