package com.bigdistributor.core.config;

import com.bigdistributor.biglogger.adapters.Log;

import java.io.*;
import java.util.*;

public class ConfigManager {

    private static final Log logger = Log.getLogger(ConfigManager.class.getName());
    private final static String CONFIG_FILE = "bigdistributor.properties";
    private static Map<PropertiesKeys, Object> config;

    public static void init() {
        logger.info("Init App Config..");
        try {
            File f = new File(ConfigManager.class.getResource("/").getPath(), CONFIG_FILE);
            if (f.exists()) {
                logger.info("Log Config: " + f.getAbsolutePath());
                config = loadConfig(f);
            } else {
                logger.info("Create Config..");
                Properties prop = createConfig();
                config = formatProperties(prop);
//                saveConfig(prop, f);
            }
        } catch (IOException | NullPointerException e) {
            logger.error(e.toString());
            Properties prop = createConfig();
            config = formatProperties(prop);
        }
    }

    public static Map<PropertiesKeys, Object> getConfig() {
        if (config == null) {
            init();
        }
        return config;
    }

    private static void saveConfig(Properties prop, File f) throws IOException {
        OutputStream output = new FileOutputStream(f);
        prop.store(output, null);
    }

    private static Map<PropertiesKeys, Object> loadConfig(File f) throws IOException {
//        File f = FileResourcesUtils.getFileFromResource(config);
//        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(f));
        return formatProperties(appProps);
    }

    private static Map<PropertiesKeys, Object> formatProperties(Properties appProps) {
        List<PropertiesKeys> proprietiesNotFound = new LinkedList<>(Arrays.asList(PropertiesKeys.values()));
        Map<PropertiesKeys, Object> appConfig = new HashMap<>();
        Enumeration<String> enums = (Enumeration<String>) appProps.propertyNames();
        while (enums.hasMoreElements()) {
            String key = enums.nextElement();
            String value = appProps.getProperty(key);
            logger.info(key + " : " + value);
            try {
                PropertiesKeys propKey = PropertiesKeys.getPropForKey(key);
                proprietiesNotFound.remove(propKey);
                Object propValue = propKey.objectOf(value);
                appConfig.put(propKey, propValue);
            } catch (IllegalArgumentException e) {
                logger.error("Invalid property not found : " + key + " -> " + value);
            } catch (ClassCastException e) {
                logger.error("Invalid value for property : " + key + " -> " + value + PropertiesKeys.getPropForKey(key).getDefaultValue().getClass());
                appConfig.put(PropertiesKeys.valueOf(key), PropertiesKeys.valueOf(key).getDefaultValue());
            }
        }
        for (PropertiesKeys prop : proprietiesNotFound) {
            logger.error("Property not found, set default : " + prop.getKey() + " -> " + prop.getDefaultValue());
            appConfig.put(prop, prop.getDefaultValue());
        }
        return appConfig;
    }

    private static Properties createConfig() {
        Properties prop = new Properties();
        for (PropertiesKeys propKeys : PropertiesKeys.values()) {
            prop.setProperty(propKeys.getKey(), String.valueOf(propKeys.getDefaultValue()));
        }
        return prop;
    }
}
