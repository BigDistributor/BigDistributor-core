package com.bigdistributor.io;

import com.bigdistributor.biglogger.adapters.Log;
import org.apache.commons.io.FilenameUtils;

import java.lang.invoke.MethodHandles;

public enum DataExtension {
    N5("n5"),
    TIF("tiff"),
    JAR("jar"),
    XML("xml"),
    MODEL("model");

    private static Log logger = Log.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private final String extensionName;

    DataExtension(String extension) {
        extensionName = extension;
    }

	public String getExtensionName() {
		return extensionName;
	}

	public static DataExtension fromString(String key) {
		for( DataExtension p: DataExtension.values()){
			if (p.getExtensionName().equalsIgnoreCase(key))
				return p;
		}
		throw new IllegalArgumentException();
	}

    public String file(String name) {
        return String.format("%s.%s", name, toString());
    }

    public String toString() {
        return this.name();
    }

    public static DataExtension fromURI(String path) {
        String extension = FilenameUtils.getExtension(path);
        return DataExtension.fromString(extension);
    }

    public static String removeExtension(String path) {
        return FilenameUtils.removeExtension(path);
    }
}