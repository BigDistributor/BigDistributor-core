package com.bigdistributor.io.serializers;

import com.bigdistributor.io.JsonSerializerDeserializer;
import com.google.gson.*;
import net.imglib2.Interval;
import net.preibisch.mvrecon.fiji.spimdata.boundingbox.BoundingBox;

import java.lang.reflect.Type;

public class IntervalSerializer implements JsonSerializerDeserializer<Interval> {

    @Override
    public Interval deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return new Gson().fromJson(json, BoundingBox.class);
    }

    @Override
    public JsonElement serialize(Interval src, Type type, JsonSerializationContext context) {
        BoundingBox vs = new BoundingBox(src);
        JsonElement x = new GsonBuilder().create().toJsonTree(vs);
        return x;
    }

}
