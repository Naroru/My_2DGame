package Frames;


import Characters.AI;
import Characters.Character;
import Enums.Directions;
import Managers.FrameManagers;
import Managers.IconManager;
import Managers.ManagerPlayerAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameField extends JFrame implements KeyListener {

    private Character  mainHero;
    private Character NPS;
    private ManagerPlayerAction managerPlayerAction;

    public static final int FLOOR_Y_COORDINATE = 365;

    public GameField()  {

        FrameManagers.setDefaultSettingsToFrame(this);
        this.setSize(1076, 540);
        this.addKeyListener(this);

        JLabel enviroment = createEnviroment();

        mainHero = Character.createCharacter(100,FLOOR_Y_COORDINATE,"mainHeroPicturesFolder",3,enviroment, Directions.RIGHT);
        NPS = Character.createCharacter(660, FLOOR_Y_COORDINATE,"mainHeroPicturesFolder", 2,enviroment, Directions.LEFT);
        NPS.setDelayMillsec(9);

        //AI.npsAIStart(NPS);

        this.add(enviroment,BorderLayout.CENTER);

    }

    private static JLabel createEnviroment() {
        //creating Enviroment
        JLabel enviroment = new JLabel();
        enviroment.setSize(1076,540);
        enviroment.setLayout(null);
        enviroment.setIcon(IconManager.getImageIcon("scenePicturesFolder", "scene1PictureName"));
        return enviroment;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
            managerPlayerAction.setLeftMovingButtonActive(true);
        else if  (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
            managerPlayerAction.setRightMovingButtonActive(true);
        else if (e.getKeyCode() == KeyEvent.VK_W)
            managerPlayerAction.setJumpButtonActive(true);
        else if (e.getKeyCode() == KeyEvent.VK_SPACE)
            managerPlayerAction.setFightButtonActive(true);
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
            managerPlayerAction.setLeftMovingButtonActive(false);
        else if  (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
            managerPlayerAction.setRightMovingButtonActive(false);
        else if (e.getKeyCode() == KeyEvent.VK_W)
            managerPlayerAction.setJumpButtonActive(false);
        else if (e.getKeyCode() == KeyEvent.VK_SPACE)
            managerPlayerAction.setFightButtonActive(false);
/*
       boolean keyMovingReleased = e.getKeyCode() == KeyEvent.VK_LEFT
                || e.getKeyCode() == KeyEvent.VK_A
                || (e.getKeyCode() == KeyEvent.VK_RIGHT
                || e.getKeyCode() == KeyEvent.VK_D);

       boolean keyFightingReleased = e.getKeyCode() == KeyEvent.VK_SPACE;
      if(keyMovingReleased || keyFightingReleased)
        mainHero.stay();*/
    }
}
