package com.example.test.hw1;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlotItem {
    public List<Float> x;
    public List<Float> y;
    public int color;
    public float maxX;
    public float minX;
    public float maxY;
    public float minY;

    public PlotItem(List<Float> x, List<Float> y, int color) {
        this.color = color;
        this.x = x;
        this.y = y;
        init();
    }

    public PlotItem(List<Float> x, PlotFunction func, int color) {
        this.color = color;
        this.x = x;
        y = new ArrayList<>(x.size());
        for (int i = 0; i < x.size(); i++)
            y.add(func.y(x.get(i)));
        init();
    }

    private void init() {
        minX = x.get(0);
        maxX = x.get(x.size() - 1);
        maxY = Collections.max(y);
        minY = Collections.min(y);
    }

    public interface PlotFunction {
        public float y(float x);
    }
}
