package com.bigdistributor.core.config;

import java.io.*;
import java.util.Map;
import java.util.Properties;

public class ConfigManager {
    
    private final static String CONFIG_FILE = "bigdistributor.properties";
    private static Map<PropertiesKeys,Object> config ;
    
    public static void init() throws IOException {
        File f = new File(ConfigManager.class.getResource("/").getPath(),CONFIG_FILE);
        if (f.exists()){
            config = loadConfig(f);
        }else{
            config = createConfig(f);
        }
    }

    private static Map<PropertiesKeys, Object> loadConfig(File f) throws IOException {
//        File f = FileResourcesUtils.getFileFromResource(config);
//        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(f));
        return null;
    }

    private static Map<PropertiesKeys, Object> createConfig(File f) throws IOException {
        try (OutputStream output = new FileOutputStream(f)) {
            Properties prop = new Properties();
            for(PropertiesKeys propKeys: PropertiesKeys.values()){
                prop.setProperty(propKeys.getKey(),String.valueOf(propKeys.getDefaultValue()));
            }
            prop.store(output, null);
        }
        return null;
    }
}
