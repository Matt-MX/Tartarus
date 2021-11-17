package com.mattmx.tartarus.components;

import com.google.gson.*;
import com.mattmx.tartarus.components.Component;

import java.lang.reflect.Type;

public class ComponentDeserializer implements JsonSerializer<Component>, JsonDeserializer<Component> {

    @Override
    public Component deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String t = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");
        try {
            return jsonDeserializationContext.deserialize(element, Class.forName(t));
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Unknown element ," + type, e);
        }
    }

    @Override
    public JsonElement serialize(Component component, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(component.getClass().getCanonicalName()));
        result.add("properties", jsonSerializationContext.serialize(component, component.getClass()));
        return result;
    }
}
