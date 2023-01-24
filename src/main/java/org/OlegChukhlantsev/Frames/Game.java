package org.OlegChukhlantsev.Frames;


import org.OlegChukhlantsev.Characters.AI;
import org.OlegChukhlantsev.Characters.Character;
import org.OlegChukhlantsev.Characters.InteractionManager;
import org.OlegChukhlantsev.Characters.Sounds.Sound;
import org.OlegChukhlantsev.CommonManagers.FrameManagers;
import org.OlegChukhlantsev.CommonManagers.PropertiesManager;
import org.OlegChukhlantsev.CommonManagers.ThreadsWaiting;
import org.OlegChukhlantsev.Icons.IconManager;
import org.OlegChukhlantsev.Characters.ManagerPlayerAction;
import org.OlegChukhlantsev.GameObjects.GameObject;
import org.OlegChukhlantsev.GameObjects.GameObjectManager;
import org.OlegChukhlantsev.GameObjects.Portion;


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
    private JLabel mainHeroLifeBar;
    private JLabel npsLifeBar;
    private Sound battleSound;

    private List <GameObject> gameObjects;

    private boolean isFinish;
    private boolean isStarted;

    private final float STANDART_MUSIC_VOLUME = (float) 0.8;
    public static final int FLOOR_Y_COORDINATE = 365;
    public static final int WIDTH = 1076;
    public static final int HEIGHT = 540;
    private static final int LIFE_BAR_HEIGHT = 150;
    private static final int LIFE_BAR_WIDTH = 10;

    private static final int MAIN_HERO_LIFEBAR_X = 10;
    private static final int MAIN_HERO_LIFEBAR_Y = 10;
    private static final int NPS_LIFEBAR_X = WIDTH - 35;
    private static final int NPS_LIFEBAR_Y = 10;


    public Game()  {

        FrameManagers.setDefaultSettingsToFrame(this);
        this.setSize(WIDTH, HEIGHT);
        this.addKeyListener(this);

        gameObjects = new ArrayList<>();

    }

    private JLabel showLabelInfo()
    {
       String info = "W - прыжок, A - влево, D - вправо, Пробел - атака";

        JLabel infoLabel = new JLabel();
        infoLabel.setFont(new Font(null,  Font.PLAIN, 25));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setBounds(250, 50, 600, 200);
        infoLabel.setText(info);
        environment.add(infoLabel);
        return infoLabel;
    }

    public void startGame()
    {

        environment = createEnvironment();

        InteractionManager.game = this;
        GameObjectManager.game = this;
        AI.game = this;


        mainHero = Character.createMainHero(this);
        NPS = Character.createNPS(this);

        addHealthPortion();
        drawLifeBars();
        JLabel info = showLabelInfo();

        this.add(environment,BorderLayout.CENTER);

        Sound startSound = new Sound(PropertiesManager.getNotNullableProperty("startMusic"), STANDART_MUSIC_VOLUME);
        battleSound = new Sound(PropertiesManager.getNotNullableProperty("battleMusic"),STANDART_MUSIC_VOLUME);


        startSound.play();
        startSound.join();
        startSound.close();

        info.setText("");

        managerPlayerAction.setMainHero(mainHero);
        AI.npsAIStart();

        isStarted = true;
        battleSound.play();

    }

    private static JLabel createEnvironment() {
        JLabel environment = new JLabel();
        environment.setSize(1076,540);
        environment.setLayout(null);
        environment.setIcon(IconManager.getImageIcon("scenePicturesFolder", "scene1PictureName"));
        return environment;
    }

    private void drawLifeBars()
    {
        mainHeroLifeBar = new JLabel();
        setLifeBarProps(mainHeroLifeBar,Color.GREEN,MAIN_HERO_LIFEBAR_X,MAIN_HERO_LIFEBAR_Y);

        JLabel additionalRedLifeBar = new JLabel();
        setLifeBarProps(additionalRedLifeBar,Color.RED,MAIN_HERO_LIFEBAR_X,MAIN_HERO_LIFEBAR_Y);

        npsLifeBar = new JLabel();
        setLifeBarProps(npsLifeBar,Color.GREEN,NPS_LIFEBAR_X,NPS_LIFEBAR_Y);

        JLabel additionalRedLifeBar2 = new JLabel();
        setLifeBarProps(additionalRedLifeBar2,Color.RED,NPS_LIFEBAR_X,NPS_LIFEBAR_Y);

        environment.add(mainHeroLifeBar);
        environment.add(npsLifeBar);
        environment.add(additionalRedLifeBar);
        environment.add(additionalRedLifeBar2);
    }

    public void checkFinishGame()
    {
        if( !isFinish && (mainHero.getCurrent_health()<=0 || NPS.getCurrent_health() <= 0)) {

            isFinish = true;
            battleSound.stop();
            battleSound.close();

            JLabel gameFinish = new JLabel();
            gameFinish.setFont(new Font(null,  Font.PLAIN, 55));
            gameFinish.setForeground(Color.WHITE);
            gameFinish.setBounds(450, 150, 200, 50);
            environment.add(gameFinish);


            if (mainHero.getCurrent_health() <= 0) {
                mainHero.die();
                NPS.victory();
                gameFinish.setText(" Defeat!");
                Sound.playSound(PropertiesManager.getNotNullableProperty("defeatMusic"));

            } else {
                NPS.die();
                mainHero.victory();
                gameFinish.setText("Victory!");
                Sound.playSound(PropertiesManager.getNotNullableProperty("victoryMusic"));
            }
        }
    }

    private void setLifeBarProps(JLabel lifebar, Color color, int x, int y)
    {
        lifebar.setOpaque(true);
        lifebar.setBackground(color);
        lifebar.setBounds(x, y, LIFE_BAR_WIDTH, LIFE_BAR_HEIGHT);
    }
    public void updateLifeBars()
    {

        float mainHeroCoefficient =  (float)mainHero.getCurrent_health()  / mainHero.getInitial_health();
        float npsCoefficient = (float) NPS.getCurrent_health() / NPS.getInitial_health();

        int  mainHeroNewY=  LIFE_BAR_HEIGHT - (int) (LIFE_BAR_HEIGHT * mainHeroCoefficient) + MAIN_HERO_LIFEBAR_Y;
        int  npsNewY= LIFE_BAR_HEIGHT - (int) (LIFE_BAR_HEIGHT * npsCoefficient) + NPS_LIFEBAR_Y;

        mainHeroLifeBar.setBounds(MAIN_HERO_LIFEBAR_X,
                mainHeroNewY,
                LIFE_BAR_WIDTH,
                (int) (LIFE_BAR_HEIGHT * mainHeroCoefficient));

        npsLifeBar.setBounds(NPS_LIFEBAR_X,
                npsNewY,
                LIFE_BAR_WIDTH,
                (int) (LIFE_BAR_HEIGHT * npsCoefficient));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public boolean isFinish() {
        return isFinish;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (isStarted && !isFinish) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
                managerPlayerAction.moveMainHeroLeft();
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
                managerPlayerAction.moveMainHeroRight();
            else if (e.getKeyCode() == KeyEvent.VK_W)
                managerPlayerAction.jumpMainHero();
            else if (e.getKeyCode() == KeyEvent.VK_SPACE)
                managerPlayerAction.fightMainHero();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

        if (isStarted && !isFinish)  {
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
                managerPlayerAction.stopMainHeroMovingLeft();
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
                managerPlayerAction.stopMainHeroMovingRight();
            else if (e.getKeyCode() == KeyEvent.VK_SPACE)
                managerPlayerAction.stopMainHeroFight();
        }
    }

    public JLabel getEnvironment() {
        return environment;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    private void addHealthPortion()
    {

        ImageIcon healthPortionIcon = IconManager.getImageIcon("healthPortionIcon");
        Portion healthPortion = new Portion(healthPortionIcon);

        GameObjectManager.addObjectToGame(healthPortion);
    }

    public Character getMainHero() {
        return mainHero;
    }

    public Character getNPS() {
        return NPS;
    }
}
