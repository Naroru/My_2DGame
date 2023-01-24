package org.OlegChukhlantsev.CommonManagers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

public class PropertiesManager {

    public static String getNotNullableProperty(String propName) {

        Optional<String>  optional  = getNullableProperty(propName);
        return optional.orElseThrow(() -> new RuntimeException("No such property defined!"));
    }


    public static Optional<String> getNullableProperty(String propName) {

        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String appConfigPath = rootPath + "MyApp.properties";

        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(appProps.getProperty(propName));

    }
}
