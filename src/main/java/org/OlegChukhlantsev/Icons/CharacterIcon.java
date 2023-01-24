package org.OlegChukhlantsev.Icons;

import javax.swing.*;
import java.net.URL;

public class CharacterIcon extends ImageIcon {

    private final int leftBorderBodyOffset;
    private final int rightBorderBodyOffset;

    public CharacterIcon(URL location, int leftBorderBodyOffset, int rightBorderBodyOffset ) {
        super(location);
        this.leftBorderBodyOffset = leftBorderBodyOffset;
        this.rightBorderBodyOffset = rightBorderBodyOffset;
    }


    public int getLeftBorderBodyOffset() {
        return leftBorderBodyOffset;
    }

    public int getRightBorderBodyOffset() {
        return rightBorderBodyOffset;
    }
}
