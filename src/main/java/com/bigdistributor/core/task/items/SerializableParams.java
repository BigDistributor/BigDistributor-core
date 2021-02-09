package com.bigdistributor.core.task.items;

import com.bigdistributor.biglogger.adapters.Log;
import com.bigdistributor.io.JsonSerializerDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

public class SerializableParams<T extends SerializableParams> implements Serializable {
    private static final long serialVersionUID = -3611993522419283535L;
    private static final Log logger = Log.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
//    private final Class<T> type;
    T type;

    protected static Map<Class, JsonSerializerDeserializer> serializers;
    protected GsonBuilder builder;

    public SerializableParams() {
        init();
        this.builder = new GsonBuilder().serializeSpecialFloatingPointValues().enableComplexMapKeySerialization();
        for (Map.Entry<Class, JsonSerializerDeserializer> entry : serializers.entrySet()) {
            builder.registerTypeHierarchyAdapter(entry.getKey(), entry.getValue());
        }
    }

    protected void init() {
        serializers = new HashMap<>();
    }

    public Gson getGson() {
        return this.builder.create();
    }

    public void toJson(File file) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
        try (PrintWriter out = new PrintWriter(file)) {
            String json = getGson().toJson(this);
            out.print(json);
            out.flush();
            out.close();
            logger.info("File saved: " + file.getAbsolutePath() + " | Size: " + file.length());
        } catch (IOException e) {
            logger.error(e.toString());
        }
        if (!checkSerialization(file)) {
            throw new JsonIOException("Objects are not equals!");
        }
        ;
    }

    private Boolean checkSerialization(File file) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
        SerializableParams serializedClass = fromJson(file);
        if (this == serializedClass) {
            return true;
        }
        return false;

    }

    public SerializableParams fromJson(File file) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
        return getGson().fromJson(new FileReader(file), type.getClass());
    }
    public SerializableParams fromJsonString(String json) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
        return getGson().fromJson(json, type.getClass());
    }

}
