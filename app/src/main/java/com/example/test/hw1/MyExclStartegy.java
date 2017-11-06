package com.example.test.hw1;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.util.ArrayList;
import java.util.List;


public class MyExclStartegy implements ExclusionStrategy {
    private ArrayList<Class> classes;
    private ArrayList<String> fields;

    public MyExclStartegy(List<String> exclude) throws ClassNotFoundException {
        classes = new ArrayList<>();
        fields = new ArrayList<>();
        for (String str : exclude) {
            classes.add(Class.forName(str.substring(0, str.lastIndexOf("."))));
            fields.add(str.substring(str.lastIndexOf(".")+1));
        }
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        for (int i = 0; i < classes.size(); i++)
            if (f.getDeclaringClass() == classes.get(i) && f.getName().equals(fields.get(i)))
                return true;
        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
