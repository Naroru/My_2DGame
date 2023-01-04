package Managers;

import Characters.Character;
import Enums.ActionTypes;
import Enums.CharacterOrientations;
import Frames.Game;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class IconManager {

    private static final String GIF_FILE_EXTENSION = ".gif";

    private static Map<String, Integer> offsetMap;

    static {
        offsetMap = new HashMap<>();
        offsetMap.put("/gifs/characters/mainHero/fightLeft.gif", -95);
    }

    public static int getPictureOffset(String fullPath) {

        String pathToResourceres = IconManager.class.getResource("/").getFile();

        String url = fullPath.substring(fullPath.indexOf(pathToResourceres));
        Optional<Integer> offset = Optional.ofNullable(offsetMap.get(url));
        return offset.orElse(0);
    }

    private static String getActionIconFilename(ActionTypes actionTypes, CharacterOrientations characterOrientations) {
        String action = "";
        String orientation = "";

        action = switch (actionTypes) {
            case JUMP -> "jump";
            case MOVE -> "move";
            case FIGHT -> "fight";
            case STAY -> "stay";
        };

        orientation = switch (characterOrientations) {
            case LEFT -> "Left";
            case RIGHT -> "Right";
        };

        String iconName = action + orientation + GIF_FILE_EXTENSION;
        return iconName;
    }

    public static URL getActionIconURL(Character.CharacterLabel characterLabel, ActionTypes actionType)
    {

        String pictureName = getActionIconFilename(actionType, characterLabel.getCharacterOrientations());
        String folder = PropertiesManager.getProperty(characterLabel.getPropertyPicturesFolder());

        URL actionIconURL = IconManager.class.getResource(folder+pictureName);

        return actionIconURL;
    }

    public static ImageIcon getImageIcon(String PropertyPicturesFolder, String PropertyPicName)
    {
        String folder = PropertiesManager.getProperty(PropertyPicturesFolder);
        String fileName =  PropertiesManager.getProperty(PropertyPicName);

        URL sceneURL = IconManager.class.getResource(folder+fileName);
        return new ImageIcon(sceneURL);

    }
}