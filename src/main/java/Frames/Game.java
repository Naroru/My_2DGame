package Frames;


import Characters.AI;
import Characters.Character;
import Managers.FrameManagers;
import Managers.IconManager;
import Characters.ManagerPlayerAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JFrame implements KeyListener {

    private Character  mainHero;
    private Character NPS;
    private ManagerPlayerAction managerPlayerAction = new ManagerPlayerAction();

    public static final int FLOOR_Y_COORDINATE = 365;

    public Game()  {


        FrameManagers.setDefaultSettingsToFrame(this);
        this.setSize(1076, 540);
        this.addKeyListener(this);

        JLabel environment = createEnviroment();

        mainHero = Character.createMainHero(environment);
        managerPlayerAction.setMainHero(mainHero);

        NPS = Character.createNPS(environment);
        AI.npsAIStart(NPS, mainHero);




        this.add(environment,BorderLayout.CENTER);

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
            managerPlayerAction.moveMainHeroLeft();
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
            managerPlayerAction.moveMainHeroRight();
        else if (e.getKeyCode() == KeyEvent.VK_W)
            managerPlayerAction.jumpMainHero();
        else if (e.getKeyCode() == KeyEvent.VK_SPACE)
            managerPlayerAction.fightMainHero();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT  || e.getKeyCode() == KeyEvent.VK_A)
            managerPlayerAction.stopMainHeroMovingLeft();
        else if  (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
            managerPlayerAction.stopMainHeroMovingRight();
        else if (e.getKeyCode() == KeyEvent.VK_SPACE)
            managerPlayerAction.stopMainHeroFight();

    }
}
