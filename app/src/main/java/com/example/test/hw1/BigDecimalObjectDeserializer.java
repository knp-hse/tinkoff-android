package com.example.test.hw1;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;


public class BigDecimalObjectDeserializer implements JsonDeserializer<BigDecimalObject> {
    @Override
    public BigDecimalObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        BigDecimalObject ans = null;
        if (json.isJsonObject()) {
            Set<Map.Entry<String, JsonElement>> entries = json.getAsJsonObject().entrySet();
            if (entries.size() > 0) {
                Map.Entry<String, JsonElement> entry = entries.iterator().next();
                ans = new BigDecimalObject (new BigDecimal(entry.getValue().getAsString().replace(',', '.')));
            }
        }
        return ans;
    }
}
