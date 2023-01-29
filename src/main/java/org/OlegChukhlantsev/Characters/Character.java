package org.OlegChukhlantsev.Characters;

import org.OlegChukhlantsev.Sounds.Sound;
import org.OlegChukhlantsev.Enums.ActionTypes;
import org.OlegChukhlantsev.Enums.CharacterOrientations;
import org.OlegChukhlantsev.Enums.CharacterTypes;
import org.OlegChukhlantsev.Frames.Game;
import org.OlegChukhlantsev.Icons.IconManager;
import org.OlegChukhlantsev.CommonManagers.PropertiesManager;
import org.OlegChukhlantsev.CommonManagers.ThreadsWaiting;
import org.OlegChukhlantsev.Icons.CharacterIcon;

import javax.swing.*;

public class Character {

    private static final int DELAY_MILLISEC = 15;
    private static final int Y_SPEED_MOVEMENT = 0;

    private CharacterLabel characterLabel;
    private CharacterTypes type;
    private int xSpeedMovenment;

    private volatile boolean isMoving = false;
    private volatile boolean isJumping = false;
    private  volatile boolean isFighting = false;
    private  volatile boolean isDying= false;

    private volatile int current_health;
    private int initial_health;
    private int attackRange;

    private boolean mainHero;

    public static Character createNPS(Game game)
    {
        int yPos = Game.FLOOR_Y_COORDINATE;
        int xPos = Integer.parseInt(PropertiesManager.getNotNullableProperty("nps.startPositionX"));

        JLabel environment = game.getEnvironment();

        Character nps = new Character();
        nps.mainHero = false;
        nps.initial_health = Integer.parseInt(PropertiesManager.getNotNullableProperty("nps.health"));
        nps.xSpeedMovenment = Integer.parseInt(PropertiesManager.getNotNullableProperty("nps.xSpeedMovement"));
        nps.characterLabel = nps.new CharacterLabel();

        // Для рандомных нпс надо присваивать различные типы, но пока оставим так
        nps.type = CharacterTypes.warrior;
        nps.characterLabel.setCharacterOrientations(CharacterOrientations.LEFT);

        return createCharacterContinue(yPos, xPos, environment, nps);
    }

    private static Character createCharacterContinue(int yPos, int xPos, JLabel environment, Character character) {

        character.current_health = character.initial_health;
        character.characterLabel.updateCharacterLabelIcon(ActionTypes.STAY);
        character.attackRange =  Integer.parseInt(PropertiesManager.getNotNullableProperty(character.type+".attackRange"));

        Icon characterIcon =  character.characterLabel.getIcon();
        character.characterLabel.setBounds(xPos, yPos - characterIcon.getIconHeight(),characterIcon.getIconWidth(),characterIcon.getIconHeight());

        environment.add(character.characterLabel);

        return character;
    }

    public static Character createMainHero(Game game)
    {
        int yPos = Game.FLOOR_Y_COORDINATE;
        int xPos = Integer.parseInt(PropertiesManager.getNotNullableProperty("mainHero.startPositionX"));

        JLabel environment = game.getEnvironment();

        Character mainHero = new Character();
        mainHero.mainHero = true;
        mainHero.type = CharacterTypes.mainHero;

        mainHero.initial_health = Integer.parseInt(PropertiesManager.getNotNullableProperty("mainHero.health"));
        mainHero.xSpeedMovenment = Integer.parseInt(PropertiesManager.getNotNullableProperty("mainHero.xSpeedMovement"));

        mainHero.characterLabel = mainHero.new CharacterLabel();
        mainHero.characterLabel.setCharacterOrientations(CharacterOrientations.RIGHT);


        return createCharacterContinue(yPos, xPos, environment, mainHero);
    }

    public Thread move() {

        Thread movingThread = new Thread(() -> {

            if (!isJumping && !isFighting)
                characterLabel.updateCharacterLabelIcon(ActionTypes.MOVE);


            Sound move_sound = new Sound("/music/" + type + "/walking.wav");
            // упрощаем в рамках пет проекта. Так громкость вообще можно хранить в .properties
            // или в БД в таблице настроек
            move_sound.setVolume(mainHero ? 1 : (float) 0.9);

            if (!isMoving) {

                isMoving = true;

                while (isMoving) {

                    if(isFighting || isJumping)
                        move_sound.stop();
                    else if (!move_sound.isPlaying()) {
                        move_sound.play();
                    }

                    int xSpeed = xSpeedMovenment;

                    if (characterLabel.isFightAnimationIsOn())
                        xSpeed = 0;

                    else if (isJumping)
                        xSpeed = xSpeedMovenment / 2;

                    characterLabel.moveCharacterLabelByXYCoordinates(xSpeed, Y_SPEED_MOVEMENT);
                }
                move_sound.stop();
                move_sound.close();
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

                int xJumpingSpeed = xSpeedMovenment / 3;

                // jumping up
                for (int i = -20; i < 0; i++) {
                    int YSpeed = i / 2;
                    characterLabel.moveCharacterLabelByXYCoordinates(xJumpingSpeed, YSpeed);
                }
                //falling down
                for (int i = 0; i < 20; i++) {
                    int YSpeed = i / 2;
                    characterLabel.moveCharacterLabelByXYCoordinates(xJumpingSpeed, YSpeed);
                }

                isJumping = false;
            }
        });

        jumpThread.start();

        return jumpThread;
    }

    public void  die()
    {


        Sound.replayDieSound(type);

        isFighting = false;
        isJumping = false;
        isMoving = false;
        isDying = true;

        characterLabel.updateCharacterLabelIcon(ActionTypes.DIE);
        int animationTime = Integer.parseInt(PropertiesManager.getNotNullableProperty(type + ".dieAnimation"));
        ThreadsWaiting.wait(animationTime);

        characterLabel.setIcon(IconManager.getStaticDieIcon(type, characterLabel.characterOrientation));



    }

    public Thread fight() {
        Thread fightThread = new Thread();

        if (!isJumping && !isFighting) {
              fightThread = new Thread(() -> {

                    isFighting = true;

                    characterLabel.updateCharacterLabelIcon(ActionTypes.FIGHT);

                    while (isFighting) {

                        for (int i = 0; i < 3; i++) {
                            // animation is run
                            characterLabel.setFightAnimationIsOn(true);

                            ThreadsWaiting.wait(200); //first part animation of a hit
                            InteractionManager.characterMakeAHit(this);
                            ThreadsWaiting.wait(200); //second part animation of a hit
                            characterLabel.setFightAnimationIsOn(false);


                            if (!isFighting)
                                break;

                        }
                    }

            });
        }
        fightThread.start();

        return fightThread;
    }

    public void stay() {

        if(!isJumping && !isFighting)
            characterLabel.updateCharacterLabelIcon(ActionTypes.STAY);
    }

public void victory()
{
    isMoving = false;
    isJumping = false;
    isFighting = false;
    stay();
}
    public void setMoving(boolean moving) {
        isMoving = moving;
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

    public int getCurrent_health() {
        return current_health;
    }

    public int getInitial_health() {
        return initial_health;
    }

    public void setCurrent_health(int current_health) {
        this.current_health = current_health;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public boolean isMainHero() {
        return mainHero;
    }

    public CharacterTypes getType() {
        return type;
    }

    public boolean isDying() {
        return isDying;
    }

    //--------------------------------------------------------------------------------
    public class CharacterLabel extends JLabel
    {
        private volatile CharacterOrientations  characterOrientation = CharacterOrientations.RIGHT;
        private int characterBodyOffset;
        private boolean fightAnimationIsOn;


      //  private URL characterIconURL;

        public CharacterLabel() {
            fightAnimationIsOn = false;

        }

        public boolean isFightAnimationIsOn() {
            return fightAnimationIsOn;
        }

        public void setFightAnimationIsOn(boolean fightAnimationIsOn) {
            this.fightAnimationIsOn = fightAnimationIsOn;
        }

        private void updateCharacterLabelIcon(ActionTypes action) {

            CharacterIcon characterIcon = IconManager.getActionIcon(type, characterOrientation,action);
            setIconPosition(characterIcon);
            setIcon(characterIcon);

        }

        private void setIconPosition(CharacterIcon characterIcon) {

            //change Y position according new pic height size
            int newY = Game.FLOOR_Y_COORDINATE - characterIcon.getIconHeight();

            //change X position according new pic height
            int newIconBodyOffset = IconManager.getOffsetToDisplayCharacter(characterIcon,characterOrientation);

            characterLabel.setBounds(characterLabel.getX() + characterBodyOffset - newIconBodyOffset, newY, characterIcon.getIconWidth(), characterIcon.getIconHeight());

            characterBodyOffset = newIconBodyOffset;
        }


        private void moveCharacterLabelByXYCoordinates(int xSpeedMovement, int ySpeedMovement) {

            int x = getX();
            int y = getY();

            int nextYPosition = y + ySpeedMovement;
            int nextXPosition =  characterOrientation == CharacterOrientations.RIGHT ? x+xSpeedMovement : x-xSpeedMovement;

            if(InteractionManager.isCollisionWithEnvironmentWalls(nextXPosition, getWidth(), getCharacterOrientation())
            ||InteractionManager.isCollisionWithOtherCharacter() )
                nextXPosition = x;


            setBounds(nextXPosition, nextYPosition, getIcon().getIconWidth(), getIcon().getIconHeight());
                InteractionManager.checkInteractionWithGameobjects(Character.this);

            ThreadsWaiting.wait(DELAY_MILLISEC);

        }

        public int getLeftBodyBorder()
        {
            return getX() + ((CharacterIcon)getIcon()).getLeftBorderBodyOffset();
        }


        public int getRightBodyBorder()
        {
            return getX() + getIcon().getIconWidth() + ((CharacterIcon) getIcon()).getRightBorderBodyOffset();
        }


        public CharacterOrientations getCharacterOrientation() {
            return characterOrientation;
        }

        public void setCharacterOrientations(CharacterOrientations characterOrientations) {
            this.characterOrientation = characterOrientations;
        }


    }
}
