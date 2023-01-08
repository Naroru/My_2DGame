package GameObjects;

import CommonManagers.ThreadsWaiting;
import Frames.Game;

import javax.swing.*;
import java.util.List;

public class GameObjectManager {

    public static void addNewObjectToGame(GameObject newObject, Game game) {
        JLabel environment = game.getEnvironment();

        int newObjectWidth = newObject.getIcon().getIconWidth();
        int newObjectHeight = newObject.getIcon().getIconHeight();

        int xMaxPosition = environment.getIcon().getIconWidth() - newObjectWidth;
        int xPosition = 0;

        while (true) {
            xPosition = (int) (Math.random() * xMaxPosition);

            if (thereIsNoIntersetion(xPosition, newObjectWidth, game.getCharactersAndGameObjectLabels()))
                break;
        }

        newObject.setBounds(xPosition, Game.FLOOR_Y_COORDINATE - newObjectHeight, newObjectWidth, newObjectHeight);

        environment.add(newObject);
        moveObjectUpAndDown(newObject);

        game.getCharactersAndGameObjectLabels().add(newObject);

    }


    private static boolean thereIsNoIntersetion(int xObjectLeftBorder, int newObjectWidth, List<JLabel> labelsForCheckingIntersections) {
        boolean thereIsNoIntersetion = true;

        int objectRightBorder = xObjectLeftBorder + newObjectWidth;

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

    private static void moveObjectUpAndDown(GameObject gameObject) {

        Thread thread = new Thread(() -> {
            while (true) {

                for (int i = 0; i < 15; i++) {
                    gameObject.setBounds(gameObject.getX(), gameObject.getY() - 1, gameObject.getIcon().getIconWidth(), gameObject.getIcon().getIconHeight());
                    ThreadsWaiting.wait(80);
                }

                for (int i = 0; i < 15; i++) {
                    gameObject.setBounds(gameObject.getX(), gameObject.getY() + 1, gameObject.getIcon().getIconWidth(), gameObject.getIcon().getIconHeight());
                    ThreadsWaiting.wait(80);
                }
            }
        });
        thread.start();
    }
}
