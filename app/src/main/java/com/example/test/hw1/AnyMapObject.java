package com.example.test.hw1;

import java.util.HashMap;


public class AnyMapObject {

    private String name;
    private HashMap<String, Integer> any_map;

    public AnyMapObject(String name, HashMap<String, Integer> any_map) {
        this.name = name;
        this.any_map = any_map;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Integer> getAny_map() {
        return any_map;
    }
}
