package db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesCreator {

    public static Properties getProperties(String propertiesFile) {
        Properties properties = null;
        try (InputStream is = PropertiesCreator.class.getClassLoader().getResourceAsStream(propertiesFile)) {
            properties = new Properties();
            properties.load(is);
        } catch (IOException ioe) {
            ioe.fillInStackTrace();
        }
        return properties;
    }
}