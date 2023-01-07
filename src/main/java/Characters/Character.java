package Characters;

import Enums.ActionTypes;
import Enums.CharacterOrientations;
import Enums.Directions;
import Frames.Game;
import Managers.IconManager;
import Managers.PropertiesManager;
import Managers.ThreadsWaiting;

import javax.swing.*;
import java.net.URL;

public class Character {

    private static final int DELAY_MILLISEC = 15;
    private static final int X_SPEED_MOVEMENT = 6;
    private static final int Y_SPEED_MOVEMENT = 0;

    private CharacterLabel characterLabel;

    private volatile boolean isMoving = false;
    private volatile boolean isJumping = false;
    private volatile boolean isFighting = false;


    public static Character createNPS(JLabel scene)
    {
        int yPos = Game.FLOOR_Y_COORDINATE;
        int xPos = Integer.parseInt(PropertiesManager.getProperty("nps.startPositionX"));

        Character nps = new Character();
        nps.characterLabel = nps.new CharacterLabel();

        // Для рандомных нпс надо генерить различные pictures folder. Определить их можно в мапе в Game например
        // Пока же пишем как константу
        nps.characterLabel.setPropertyPicturesFolder("nps.picturesFolder");

        nps.characterLabel.setCharacterOrientations(CharacterOrientations.LEFT);
        nps.characterLabel.updateCharacterLabelIcon(ActionTypes.STAY);

        Icon characterIcon =  nps.characterLabel.getIcon();
        nps.characterLabel.setBounds(xPos, yPos - characterIcon.getIconHeight(),characterIcon.getIconWidth(),characterIcon.getIconHeight());

        scene.add(nps.characterLabel);
        return nps;
    }

    public static Character createMainHero(JLabel scene)
    {
        int yPos = Game.FLOOR_Y_COORDINATE;
        int xPos = Integer.parseInt(PropertiesManager.getProperty("mainHero.startPositionX"));

        Character mainHero = new Character();

        mainHero.characterLabel = mainHero.new CharacterLabel();
        mainHero.characterLabel.setPropertyPicturesFolder("mainHero.picturesFolder");
        mainHero.characterLabel.setCharacterOrientations(CharacterOrientations.RIGHT);
        mainHero.characterLabel.updateCharacterLabelIcon(ActionTypes.STAY);

        Icon characterIcon =  mainHero.characterLabel.getIcon();
        mainHero.characterLabel.setBounds(xPos, yPos - characterIcon.getIconHeight(),characterIcon.getIconWidth(),characterIcon.getIconHeight());

        scene.add(mainHero.characterLabel);
        return mainHero;
    }
    public Thread move() {

        Thread movingThread = new Thread(() -> {
            if (!isMoving) {

                isMoving = true;

                if (!isJumping && !isFighting)
                    characterLabel.updateCharacterLabelIcon(ActionTypes.MOVE);

                while (isMoving && !isFighting)
                    characterLabel.moveCharacterLabelByXYCoordinates(X_SPEED_MOVEMENT, Y_SPEED_MOVEMENT);

            }
        });

        movingThread.start();
        return movingThread;
    }

    public CharacterLabel getCharacterLabel() {
        return characterLabel;
    }

    public Thread jump() {

        Thread jumpThread = new Thread(() -> {

            if (!isJumping) {

                isJumping = true;
                characterLabel.updateCharacterLabelIcon(ActionTypes.JUMP);

                int xJumpingSpeed = X_SPEED_MOVEMENT / 2;

                // jumping up
                for (int i = -18; i < 0; i++) {
                    int YSpeed = i / 2;
                    characterLabel.moveCharacterLabelByXYCoordinates(xJumpingSpeed, YSpeed);
                }
                //falling down
                for (int i = 0; i < 18; i++) {
                    int YSpeed = i / 2;
                    characterLabel.moveCharacterLabelByXYCoordinates(xJumpingSpeed, YSpeed);
                }

                isJumping = false;
            }
        });

        jumpThread.start();

        return jumpThread;
    }

    public Thread fight() {

        Thread fightThread = new Thread(() -> {

            if (!isJumping && !isFighting) {

                isFighting = true;
                isMoving = false;

                characterLabel.updateCharacterLabelIcon(ActionTypes.FIGHT);

                while (isFighting) {

                    for (int i = 0; i < 3; i++) {

                        ThreadsWaiting.wait(400);

                        if (!isFighting)
                            break;
                    }
                }
            }
        });

        fightThread.start();

        return fightThread;
    }

    public void stay() {

        if(!isJumping && !isFighting)
            characterLabel.updateCharacterLabelIcon(ActionTypes.STAY);
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public void setFighting(boolean fighting) {
        isFighting = fighting;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public boolean isFighting() {
        return isFighting;
    }

    //--------------------------------------------------------------------------------
    public class CharacterLabel extends JLabel
    {
        private volatile CharacterOrientations  characterOrientations = CharacterOrientations.RIGHT;
        private int iconOffset;

        private String PropertyPicturesFolder;

        private void updateCharacterLabelIcon(ActionTypes action) {

            URL newIconURL = IconManager.getActionIconURL(this, action);

            ImageIcon newIcon = new ImageIcon(newIconURL);
            setIcon(newIcon);

            shiftLabelWithNewIcon(newIcon,newIconURL.getFile());
        }

        private void shiftLabelWithNewIcon(ImageIcon newIcon, String NewIconPathToFile) {

            //change Y position according new pic height size
            int newY = Game.FLOOR_Y_COORDINATE - newIcon.getIconHeight();

            //change X position according new pic height
            int OffsetNewIcon = IconManager.getPictureOffset(NewIconPathToFile);
            int offset = OffsetNewIcon - iconOffset;

            characterLabel.setBounds(characterLabel.getX() + offset, newY, newIcon.getIconWidth(), newIcon.getIconHeight());

            iconOffset = OffsetNewIcon;
        }


        private void moveCharacterLabelByXYCoordinates(int xSpeedMovement, int ySpeedMovement) {

            int x = getX();
            int y = getY();

            if (characterOrientations == CharacterOrientations.RIGHT)
                x += xSpeedMovement;
            else
                x -= xSpeedMovement;

            if(x<0)
                x = 0;
            else if (x > 1076 - getIcon().getIconWidth()) {
                x =  1076 - getIcon().getIconWidth();
            }
            y += ySpeedMovement;

            setBounds(x, y, getIcon().getIconWidth(), getIcon().getIconHeight());

            try {
                Thread.sleep(DELAY_MILLISEC);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        public void setPropertyPicturesFolder(String PropertyPicturesFolder) {
            this.PropertyPicturesFolder = PropertyPicturesFolder;
        }

        public String getPropertyPicturesFolder() {
            return PropertyPicturesFolder;
        }

        public CharacterOrientations getCharacterOrientations() {
            return characterOrientations;
        }

        public void setCharacterOrientations(CharacterOrientations characterOrientations) {
            this.characterOrientations = characterOrientations;
        }
    }
}
