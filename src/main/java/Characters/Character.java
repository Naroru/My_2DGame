package Characters;

import Enums.Directions;
import Frames.GameField;
import Managers.IconManager;

import javax.swing.*;
import java.awt.*;

public class Character extends JLabel{

    public void setxIncrement(int xIncrement) {
        this.xIncrement = xIncrement;
    }

    private  int xIncrement = 2;

    private int offsetForPic;


    private   int delayMillsec = 5;
    private int countAttackAnimation; //depends of gif file
    private volatile boolean rightOrientation = true;
    private volatile boolean  isMoving = false;
    private volatile boolean isJumping = false;
    private volatile boolean fightAnimationIsRun = false;
    private volatile boolean isFighting = false;

    private String PropertyPicturesFolder;


    public void setDelayMillsec(int delayMillsec) {
        this.delayMillsec = delayMillsec;
    }
    public void setPropertyPicturesFolder(String PropertyPicturesFolder) {
        this.PropertyPicturesFolder = PropertyPicturesFolder;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public static Character createCharacter(int xPos, int yPos, String PropertyPicturesFolder, int countAttackAnimation, JLabel scene, Directions direction)
    {
        Character character = new Character();


        character.setPropertyPicturesFolder(PropertyPicturesFolder);

        String propetyPictureName = "CharacterStayingRightPictureName";

        if (direction == Directions.LEFT)
             propetyPictureName = "CharacterStayingLeftPictureName";

        ImageIcon characterIcon = IconManager.getImageIcon(PropertyPicturesFolder,propetyPictureName);

        character.setBounds(xPos, yPos - characterIcon.getIconHeight(),characterIcon.getIconWidth(),characterIcon.getIconHeight());
        character.setIcon(characterIcon);

        scene.add(character);

        return character;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public boolean isMoving() {
        return isMoving;
    }
    public void setRightOrientation(boolean rightOrientation) {
        this.rightOrientation = rightOrientation;
    }

    public Thread move() {
  
        Thread movingThread = new Thread(() -> {
            setMoving(true);

            if (!isJumping && !isFighting)

                setActionIcon("CharacterMovingRightPictureName", "CharacterMovingLeftPictureName");

            while (isMoving && !isFighting)
                moveCharacterByXYCoordinates(xIncrement, 0, delayMillsec);
        });

        movingThread.start();
        return movingThread;
    }


    private void moveCharacterByXYCoordinates( int xIncrement, int yIncrement, int delayMillsec) {

        int x = getX();
        int y = getY();

        if (rightOrientation)
            x += xIncrement;
        else
            x -= xIncrement;

        if(x<0)
            x = 0;
        else if (x > 1076 - getIcon().getIconWidth()) {
           x =  1076 - getIcon().getIconWidth();
        }
        y += yIncrement;

        setBounds(x, y, getIcon().getIconWidth(), getIcon().getIconHeight());


        try {
            Thread.sleep(delayMillsec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void jump() {

        if (!isJumping()) {

            setJumping(true);
            setActionIcon("CharacterJumpRightPictureName", "CharacterJumpLeftPictureName");

            Thread jumpThread = new Thread(() -> {

// jumping up
                for (int i = -12; i < 0; i++)
                    moveCharacterByXYCoordinates(xIncrement, i, 30);
//falling down
                for (int i = 0; i < 12; i++)
                    moveCharacterByXYCoordinates(xIncrement, i, 30);


                setJumping(false);

                if (isMoving()) {
                    setActionIcon("CharacterMovingRightPictureName", "CharacterMovingLeftPictureName");
                } else stay();
            });
            jumpThread.start();

        }

    }

    public Thread fight() {


            Thread jumpThread = new Thread(() -> {

                if (!isJumping  && !fightAnimationIsRun) {

                    setFighting(true);
                    setMoving(false);

                    setActionIcon("CharacterFightRightPictureName", "CharacterFightLeftPictureName");

                    while (isFighting) {

                        for (int i = 0; i < countAttackAnimation; i++) {

                            fightAnimationIsRun = true;

                             AI.wait(400);

                            if (!isFighting) {

                                fightAnimationIsRun = false;
                                if (isMoving)
                                    setActionIcon("CharacterMovingRightPictureName", "CharacterMovingLeftPictureName");
                                else
                                    stay();
                                break;
                            }

                        }


                    }
                }
            });

            jumpThread.start();

            return jumpThread;
        }


    public void stay() {
        setMoving(false);
        // setJumping(false);
          setFighting(false);

        if(!isJumping && !fightAnimationIsRun)
            setActionIcon("CharacterStayingRightPictureName", "CharacterStayingLeftPictureName");
    }

    public boolean isRightOrientation() {
        return rightOrientation;
    }

    private void setActionIcon(String propertyNameRightAction, String propertyNameLeftAction) {

        ImageIcon actionCharacterIcon;

        if (rightOrientation)
            actionCharacterIcon = IconManager.getImageIcon(PropertyPicturesFolder, propertyNameRightAction);
        else actionCharacterIcon = IconManager.getImageIcon(PropertyPicturesFolder, propertyNameLeftAction);

        setIcon(actionCharacterIcon);

        //change Y position according new pic height size
        int newY = GameField.FLOOR_Y_COORDINATE - actionCharacterIcon.getIconHeight();

        //change X position according new pic height

        IconManager icon = (IconManager) getIcon();
        int offset =  icon.getOffset() - offsetForPic; //  new offset - current offset
        this.offsetForPic = icon.getOffset();

        setBounds(getX() + offset, newY, actionCharacterIcon.getIconWidth(), actionCharacterIcon.getIconHeight());

    }

    private Character() {
    }

    public boolean isFighting() {
        return isFighting;
    }

    public void setFighting(boolean fighting) {
        isFighting = fighting;
    }
}
