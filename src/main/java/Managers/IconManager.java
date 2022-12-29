package Managers;

import Frames.GameField;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class IconManager extends ImageIcon {


private static  Map<String, Integer> offsetMap;

private int offset;


    static {
        offsetMap = new HashMap<>();
        offsetMap.put("mainHeroPicturesFolder/CharacterFightLeftPictureName", -95);
    }

    private IconManager(URL sceneURL, int offset) {
        super(sceneURL);
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public static int CheckOffsetForProperty(String propertyName)
    {
        Optional<Integer> offset = Optional.ofNullable(offsetMap.get(propertyName));
        return offset.orElse(0);
    }

    public static ImageIcon getImageIcon(String PropertyPicturesFolder, String PropertyPicName)
    {

        int offset = CheckOffsetForProperty(PropertyPicturesFolder+"/"+PropertyPicName);
        String folder = getProperty(PropertyPicturesFolder);
        String fileName =  getProperty(PropertyPicName);

        URL sceneURL = GameField.class.getResource(folder+fileName);

        return new IconManager(sceneURL,offset);

    }

    public static String getProperty(String propName)
    {
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String appConfigPath = rootPath + "MyApp.properties";

        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return  appProps.getProperty(propName);
    }
}
