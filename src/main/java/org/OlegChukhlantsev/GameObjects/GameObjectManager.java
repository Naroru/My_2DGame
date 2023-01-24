package org.OlegChukhlantsev.GameObjects;

import org.OlegChukhlantsev.CommonManagers.ThreadsWaiting;
import org.OlegChukhlantsev.Frames.Game;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameObjectManager {

    public static Game game;

    public static void addObjectToGame(GameObject newObject) {

       JLabel environment = game.getEnvironment();

        int newObjectWidth = newObject.getIcon().getIconWidth();
        int newObjectHeight = newObject.getIcon().getIconHeight();

        int xMaxPosition = environment.getIcon().getIconWidth() - newObjectWidth;
        int xPosition = 0;

        while (true) {
            xPosition = (int) (Math.random() * xMaxPosition);

            int xLeftBorder = xPosition;
            int xRightBorder = xLeftBorder + newObjectWidth;

            if (thereIsNoIntersetion(xLeftBorder, xRightBorder, game))
                break;
        }

        newObject.setBounds(xPosition, Game.FLOOR_Y_COORDINATE - newObjectHeight, newObjectWidth, newObjectHeight);

        environment.add(newObject);

        moveObjectUpAndDown(newObject);

        game.getGameObjects()
                .add(newObject);

    }

    private static boolean thereIsNoIntersetion(int xObjectLeftBorder, int objectRightBorder, Game game) {

        boolean thereIsNoIntersetion = true;

        List<JLabel> labelsForCheckingIntersections = getListOfLabelsForCheckingIntersection(game);

        for (JLabel label : labelsForCheckingIntersections) {

            int labelXLeftBorder = label.getX();
            int labelXRightBorder = labelXLeftBorder + label.getIcon().getIconWidth();

            if (!(xObjectLeftBorder > labelXRightBorder || objectRightBorder < labelXLeftBorder)) {
                thereIsNoIntersetion = false;
                break;
            }
        }

        return thereIsNoIntersetion;
    }


    public static void recreateObject (GameObject gameObject)
    {
        Thread thread = new Thread(() -> {

            game.getGameObjects().remove(gameObject);

            gameObject.setVisible(false);
            ThreadsWaiting.wait(8000);
            gameObject.setVisible(true);
            addObjectToGame(gameObject);


        });

        thread.start();

    }
   private static List<JLabel> getListOfLabelsForCheckingIntersection(Game game)
   {
       List<JLabel> labelsForCheckingIntersections = new ArrayList<>(game.getGameObjects());
       labelsForCheckingIntersections.add(game.getNPS().getCharacterLabel());
       labelsForCheckingIntersections.add(game.getMainHero().getCharacterLabel());

       return  labelsForCheckingIntersections;
   }
    private static void moveObjectUpAndDown(GameObject gameObject) {

        Thread thread = new Thread(() -> {

            int iconWidth = gameObject.getIcon().getIconWidth();
            int iconHeight = gameObject.getIcon().getIconHeight();

            while (gameObject.isVisible()) {

                for (int i = 0; i < 15; i++) {
                    gameObject.setBounds(gameObject.getX(), gameObject.getY() - 1,iconWidth, iconHeight);
                    ThreadsWaiting.wait(80);
                }

                for (int i = 0; i < 15; i++) {
                    gameObject.setBounds(gameObject.getX(), gameObject.getY() + 1, iconWidth,iconHeight);
                    ThreadsWaiting.wait(80);
                }
            }
        });
        thread.start();
    }
}
