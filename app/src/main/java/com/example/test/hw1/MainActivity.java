package com.example.test.hw1;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Float> x = new ArrayList<>();
        x.add(1.0f);
        x.add(2.0f);
        x.add(3.0f);
        x.add(4.0f);
        x.add(5.0f);
        ArrayList<Float> y = new ArrayList<>();
        y.add(2.0f);
        y.add(0.5f);
        y.add(3.7f);
        y.add(4.0f);
        y.add(1.2f);

        LabeledGraphView gr = findViewById(R.id.graph);
        gr.setGrid(1.0f, 1.0f, 1.0f, 0.5f);
        gr.setLabels("Values", "Numbers");
        ArrayList<PlotItem> list = new ArrayList<PlotItem>();
        list.add(new PlotItem(x,  y, ContextCompat.getColor(this, R.color.colorPrimaryDark)));
        list.add(new PlotItem(x, new PlotItem.PlotFunction() {
            @Override
            public float y(float x) {
                return 7 / x;
            }
        }, ContextCompat.getColor(this, R.color.colorAccent)));
        gr.setItems(list);
    }
}
