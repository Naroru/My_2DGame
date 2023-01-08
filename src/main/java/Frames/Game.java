package Frames;


import Characters.AI;
import Characters.Character;
import CommonManagers.FrameManagers;
import CommonManagers.IconManager;
import Characters.ManagerPlayerAction;
import GameObjects.GameObjectManager;
import GameObjects.Portion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Game extends JFrame implements KeyListener {

    private Character  mainHero;
    private Character NPS;
    private ManagerPlayerAction managerPlayerAction = new ManagerPlayerAction();
    private JLabel environment;
    private List <JLabel> charactersAndGameObjectLabels;

    public static final int FLOOR_Y_COORDINATE = 365;

    public Game()  {

        FrameManagers.setDefaultSettingsToFrame(this);
        this.setSize(1076, 540);
        this.addKeyListener(this);

        environment = createEnviroment();
        charactersAndGameObjectLabels = new ArrayList<>();

        mainHero = Character.createMainHero(this);
        managerPlayerAction.setMainHero(mainHero);

        NPS = Character.createNPS(this);
        AI.npsAIStart(NPS, mainHero);

        addHealthPortion();

        this.add(environment,BorderLayout.CENTER);

    }

    private static JLabel createEnviroment() {
        JLabel environment = new JLabel();
        environment.setSize(1076,540);
        environment.setLayout(null);
        environment.setIcon(IconManager.getImageIcon("scenePicturesFolder", "scene1PictureName"));
        return environment;
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

    public JLabel getEnvironment() {
        return environment;
    }

    public List<JLabel> getCharactersAndGameObjectLabels() {
        return charactersAndGameObjectLabels;
    }

    private void addHealthPortion()
    {

        ImageIcon healthPortionIcon = IconManager.getImageIcon("healthPortionIcon");
        Portion healthPortion = new Portion(healthPortionIcon);

        GameObjectManager.addNewObjectToGame(healthPortion, this);
    }
}
