package com.example.test.hw1;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;

public class DateExampleDeserializer implements JsonDeserializer<DateExample> {
    @Override
    public DateExample deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        DateExample date = null;
        if (json.isJsonObject()) {
            Set<Map.Entry<String, JsonElement>> entries = json.getAsJsonObject().entrySet();
            if (entries.size() > 0) {
                Map.Entry<String, JsonElement> entry = entries.iterator().next();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = new DateExample(sdf.parse(entry.getValue().getAsString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return date;
    }
}
