package com.example.test.hw1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String firstIn = "{\"ignored\":\"abc\", \"field\":\"value\"}";
    private static final String secondIn = " {\"name\":\"name\",\"any_map\":{\"a\":\"55\",\"b\":\"85\",\"c\":\"56\"}}";
    private static final String thirdIn = " {\"money_amount\":\"2444,88\"}";
    private static final String fourthIn = " {\"date\" : \"2017-10-30\"}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textIn1 = findViewById(R.id.firstIn);
        textIn1.setText(firstIn);
        try {
            ArrayList<String> ignore = new ArrayList<>();
            ignore.add("com.example.test.hw1.IgnoreObject.ignore");
            Gson gson1 = new GsonBuilder().setExclusionStrategies(new MyExclStartegy(ignore)).create();
            IgnoreObject obj = gson1.fromJson(firstIn, IgnoreObject.class);
            TextView textOut1 = findViewById(R.id.firstOut);
            textOut1.setText("ignored: " + obj.getIgnore() + " field: " + obj.getField());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        TextView textIn2 = findViewById(R.id.secondIn);
        textIn2.setText(secondIn);

        Gson gson2 = new GsonBuilder().registerTypeAdapter(AnyMapObject.class, new AnyMapDeserializer()).create();
        AnyMapObject obj2 = gson2.fromJson(secondIn, AnyMapObject.class);
        TextView textOut2 = findViewById(R.id.secondOut);
        String text = "name: " + obj2.getName() + " any_map: ";
        HashMap<String, Integer> hm = obj2.getAny_map();
        for (String key : hm.keySet())
            text += key + " " + hm.get(key) + ", ";
        textOut2.setText(text);

        TextView textIn3 = findViewById(R.id.thirdIn);
        textIn3.setText(thirdIn);

        Gson gson3 = new GsonBuilder().registerTypeAdapter(BigDecimalObject.class, new BigDecimalObjectDeserializer()).create();
        BigDecimalObject obj3 = gson3.fromJson(thirdIn, BigDecimalObject.class);
        TextView textOut3 = findViewById(R.id.thirdOut);
        textOut3.setText("money: " + obj3.getMoney_amount());

        TextView textIn4 = findViewById(R.id.fourthIn);
        textIn4.setText(fourthIn);

        Gson gson4 = new GsonBuilder().registerTypeAdapter(DateExample.class, new DateExampleDeserializer()).create();
        DateExample obj4 = gson3.fromJson(fourthIn, DateExample.class);
        TextView textOut4 = findViewById(R.id.fourthOut);
        textOut4.setText("date: " + obj4.getDate());
    }
}
