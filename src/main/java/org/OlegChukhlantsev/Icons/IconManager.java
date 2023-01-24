package org.OlegChukhlantsev.Icons;

import org.OlegChukhlantsev.CommonManagers.PropertiesManager;
import org.OlegChukhlantsev.Enums.ActionTypes;
import org.OlegChukhlantsev.Enums.CharacterOrientations;
import org.OlegChukhlantsev.Enums.CharacterTypes;

import javax.swing.*;
import java.net.URL;

public class IconManager {

    private static final String GIF_FILE_EXTENSION = ".gif";
    private static final String PNG_FILE_EXTENSION = ".png";

    public static int getOffsetToDisplayCharacter(CharacterIcon icon, CharacterOrientations characterOrientation)
    {

       int offset = switch (characterOrientation) {
            case RIGHT -> icon.getLeftBorderBodyOffset();
            case LEFT ->  icon.getLeftBorderBodyOffset();
        };

        return offset;
    }

    public static CharacterIcon getActionIcon(CharacterTypes iconType, CharacterOrientations orientation, ActionTypes actionType)
    {

            String pictureName = ("" + actionType + orientation + GIF_FILE_EXTENSION);
            String folder = PropertiesManager.getNotNullableProperty(iconType + ".picturesFolderGif");

            URL actionIconURL = IconManager.class.getResource(folder + pictureName);

            String propNameLeftBorderOffset = iconType + "." + actionType + orientation + ".leftBorderOffset";
            String propNameRightBorderOffset = iconType + "." + actionType + orientation + ".RightBorderOffset";

            int leftBorderBodyOffset = Integer.parseInt(PropertiesManager.getNullableProperty(propNameLeftBorderOffset).orElse("0"));
            int rightBorderBodyOffset = Integer.parseInt(PropertiesManager.getNullableProperty(propNameRightBorderOffset).orElse("0"));

            return new CharacterIcon(actionIconURL, leftBorderBodyOffset, rightBorderBodyOffset);

        }

        public  static ImageIcon getStaticDieIcon(CharacterTypes iconType, CharacterOrientations orientation)
        {
            String pictureName = "die" + orientation +"Static" + PNG_FILE_EXTENSION;
            String folder = PropertiesManager.getNotNullableProperty(iconType+".dieStaticIcon");

            URL staticCharacterIconURL = IconManager.class.getResource(folder + pictureName);

            return new ImageIcon(staticCharacterIconURL);
        }


    public static ImageIcon getImageIcon(String PropertyPicturesFolder, String PropertyPicName)
    {
        String folder = PropertiesManager.getNotNullableProperty(PropertyPicturesFolder);
        String fileName =  PropertiesManager.getNotNullableProperty(PropertyPicName);

        URL sceneURL = IconManager.class.getResource(folder+fileName);
        return new ImageIcon(sceneURL);

    }

    public static ImageIcon getImageIcon(String PropertyFullPathToIcon)
    {
        String fullPathToIcon = PropertiesManager.getNotNullableProperty(PropertyFullPathToIcon);

        URL sceneURL = IconManager.class.getResource(fullPathToIcon);
        return new ImageIcon(sceneURL);

    }
}