package Frames;


import Characters.AI;
import Characters.Character;
import Enums.Directions;
import Managers.FrameManagers;
import Managers.IconManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameField extends JFrame implements KeyListener {
    private Character  NPS;
    private static Character mainHero;
    public static final int FLOOR_Y_COORDINATE = 365;

    public static Character getMainHero() {
        return mainHero;
    }

    public GameField()  {


        FrameManagers.setDefaultSettingsToFrame(this);
        this.setSize(1076, 540);
        this.addKeyListener(this);

        //creating Enviroment
        JLabel enviroment = new JLabel();
        enviroment.setSize(1076,540);
        enviroment.setLayout(null);
        enviroment.setIcon(IconManager.getImageIcon("scenePicturesFolder", "scene1PictureName"));

        mainHero = Character.createCharacter(100,FLOOR_Y_COORDINATE,"mainHeroPicturesFolder",3,enviroment, Directions.RIGHT);

        NPS = Character.createCharacter(660, FLOOR_Y_COORDINATE,"mainHeroPicturesFolder", 2,enviroment, Directions.LEFT);
        NPS.setDelayMillsec(9);

        AI.npsAIStart(NPS);



        this.add(enviroment,BorderLayout.CENTER);

    }

    @Override
    public void keyTyped(KeyEvent e) {
     //  mainHero.stay();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (!mainHero.isMoving()) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                mainHero.setRightOrientation(false);
                mainHero.move();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                mainHero.setRightOrientation(true);
                mainHero.move();
            }


        }

        if(e.getKeyCode() == KeyEvent.VK_W)
            mainHero.jump();

        if(e.getKeyCode() == KeyEvent.VK_SPACE)
            mainHero.fight();
    }

    @Override
    public void keyReleased(KeyEvent e) {

       boolean keyMovingReleased = e.getKeyCode() == KeyEvent.VK_LEFT
                || e.getKeyCode() == KeyEvent.VK_A
                ||   (e.getKeyCode() == KeyEvent.VK_RIGHT
                || e.getKeyCode() == KeyEvent.VK_D);

       boolean keyFightingReleased = e.getKeyCode() == KeyEvent.VK_SPACE;
      if(keyMovingReleased || keyFightingReleased)
        mainHero.stay();
    }
}
