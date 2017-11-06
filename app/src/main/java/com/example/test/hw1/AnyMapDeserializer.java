package com.example.test.hw1;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class AnyMapDeserializer implements JsonDeserializer<AnyMapObject> {
    @Override
    public AnyMapObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        AnyMapObject object = null;
        if (json.isJsonObject()) {
            Set<Map.Entry<String, JsonElement>> entries = json.getAsJsonObject().entrySet();
            if (entries.size() < 2)
                return null;
            String name = null;
            HashMap<String, Integer> any_map = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry : entries) {
                switch (entry.getKey()) {
                    case "name":
                        name = entry.getValue().getAsString();
                        break;
                    case "any_map":
                        Set<Map.Entry<String, JsonElement>> elements =
                                entry.getValue().getAsJsonObject().entrySet();
                        for (Map.Entry<String, JsonElement> elem : elements) {
                            any_map.put(elem.getKey(), elem.getValue().getAsInt());
                        }
                        break;
                }
            }
            object = new AnyMapObject(name, any_map);
        }
        return object;
    }
}
