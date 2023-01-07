package Managers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class PropertiesManager {

    public static String getProperty(String propName) {

        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String appConfigPath = rootPath + "MyApp.properties";

        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return appProps.getProperty(propName);
    }


}
