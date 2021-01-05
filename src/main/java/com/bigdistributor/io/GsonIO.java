package com.bigdistributor.io;

import com.bigdistributor.biglogger.adapters.Log;
import com.bigdistributor.io.serializers.AffineTransform3DJsonSerializer;
import com.bigdistributor.io.serializers.IntervalSerializer;
import com.bigdistributor.io.serializers.ViewIdJsonSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mpicbg.spim.data.sequence.ViewId;
import net.imglib2.Interval;
import net.imglib2.realtransform.AffineTransform3D;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;


public class GsonIO {

    private static final Log logger = Log.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    public static <T> T fromJson(String path, Class<T> cls) throws Exception {
        try {
            return new Gson().fromJson(new FileReader(path), cls);
        } catch (Exception e) {
            logger.error(path + " not found !  " + e.toString());
            return null;
        }
    }

    public static void toJson(Object obj, String path) {
        toJson(obj, new File(path));
    }

    public static void toJson(Object obj, File file) {
        try (PrintWriter out = new PrintWriter(file)) {
            Gson gson = getGson();
            String json = gson.toJson(obj);
            out.print(json);
            out.flush();
            out.close();
            logger.info("File saved: " + file.getAbsolutePath() + " | Size: " + file.length());
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

    public static String toString(Object obj) {
        Gson gson = getGson();
        String json = gson.toJson(obj);
        return json;
    }

    private static Gson getGson() {
        GsonBuilder builder = new GsonBuilder()
                .serializeSpecialFloatingPointValues()
                .serializeNulls()
                .registerTypeAdapter(AffineTransform3D.class, new AffineTransform3DJsonSerializer())
                .registerTypeAdapter(ViewId.class, new ViewIdJsonSerializer())
                .registerTypeAdapter(Interval.class, new IntervalSerializer());
        return builder.create();
    }
}
