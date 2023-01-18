package org.OlegChukhlantsev.Characters;

import org.OlegChukhlantsev.CommonManagers.PropertiesManager;
import org.OlegChukhlantsev.Enums.CharacterOrientations;
import org.OlegChukhlantsev.Frames.Game;
import org.OlegChukhlantsev.GameObjects.GameObject;
import org.OlegChukhlantsev.GameObjects.GameObjectManager;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class InteractionManager {

    public static  Game game;

    public static void characterMakeAHit(Character character)
    {
        Character enemy = getEnemyOfCharacter(character);

        if (characterAchieveEnemyBody(character, enemy)) {

            String propNameDamage = character.getCharacterLabel().getCharacterIconType() + ".damage";

            int damage = Integer.parseInt(PropertiesManager.getNotNullableProperty(propNameDamage));
            enemy.setCurrent_health(enemy.getCurrent_health() - damage);

            game.updateLifeBars();
            game.checkFinishGame();

        }
    }


    public static boolean isCollisionWithOtherCharacter()
    {

        Character.CharacterLabel mainHeroLabel = game.getMainHero().getCharacterLabel();
        Character.CharacterLabel npsLabel = game.getNPS().getCharacterLabel();

        int mainHeroBodyLeftBorder = mainHeroLabel.getLeftBodyBorder();
        int mainHeroBodyRightBorder = mainHeroLabel.getRightBodyBorder();

        int npsBodyLeftBorder = npsLabel.getLeftBodyBorder();
        int npsBodyRightBorder = npsLabel.getRightBodyBorder();

        // персонаж столкнется с другим персонажем, если телом войдет в его картинку в интервал от 0 до 30 пикселей
        //  для получения позиции тела используется offset
        int maxIn = 30;

        boolean mainHeroCollideToTheLeft = mainHeroBodyRightBorder >= npsBodyLeftBorder
                && mainHeroBodyRightBorder <= npsBodyLeftBorder + maxIn
                && mainHeroLabel.getCharacterOrientation() == CharacterOrientations.RIGHT;

        boolean mainHeroCollideToTheRight = mainHeroBodyLeftBorder <= npsBodyRightBorder
                && mainHeroBodyLeftBorder >= npsBodyRightBorder - maxIn
                && mainHeroLabel.getCharacterOrientation() == CharacterOrientations.LEFT;

        return !mainHeroInJump(mainHeroLabel, npsLabel) && (mainHeroCollideToTheLeft || mainHeroCollideToTheRight) ;

    }

    public static boolean characterAchieveEnemyBody(Character character, Character enemy)
    {
       boolean characterAchieveEnemyBody;


        int characterBodyLeftBorder = character.getCharacterLabel().getLeftBodyBorder();
        int characterBodyRightBorder = character.getCharacterLabel().getRightBodyBorder();

        int enemyBodyLeftBorder = enemy.getCharacterLabel().getLeftBodyBorder();
        int enemyBodyRightBorder = enemy.getCharacterLabel().getRightBodyBorder();

        if(character.getCharacterLabel().getCharacterOrientation() == CharacterOrientations.RIGHT)
            characterAchieveEnemyBody =  characterBodyLeftBorder < enemyBodyLeftBorder
                   && characterBodyRightBorder + character.getAttackRange() >= enemyBodyLeftBorder;
        else

            characterAchieveEnemyBody = characterBodyRightBorder > enemyBodyRightBorder
                    && characterBodyLeftBorder - character.getAttackRange() < enemyBodyRightBorder;

        return  characterAchieveEnemyBody;
    }

    private static  Character getEnemyOfCharacter(Character character)
    {
        if (character.equals(game.getMainHero()))
            return game.getNPS();
        else
            return game.getMainHero();
    }
    public static boolean isCollisionWithEnvironmentWalls(int newXPosition, int IconWidth, CharacterOrientations orientations) {


        return  orientations == CharacterOrientations.LEFT && newXPosition <= 0
                ||  orientations == CharacterOrientations.RIGHT && newXPosition + IconWidth >= game.getWidth();

    }

    public static void checkInteractionWithGameobjects(Character mainHero) {

        if (mainHero.isMainHero()) {

            Character.CharacterLabel mainHeroLabel = mainHero.getCharacterLabel();

         for (GameObject gameObject : game.getGameObjects()) {

             int xLeftBorder = gameObject.getX();
             int xRightBorder = xLeftBorder + gameObject.getWidth();
             int xMiddle = xLeftBorder + gameObject.getWidth() / 2;

             if (mainHeroLabel.getLeftBodyBorder() <= xLeftBorder && mainHeroLabel.getRightBodyBorder() >= xMiddle
                     || mainHeroLabel.getRightBodyBorder() >= xRightBorder && mainHeroLabel.getLeftBodyBorder() <= xMiddle) {

                 gameObject.effectOnHero(mainHero);

                 game.updateLifeBars();

                 GameObjectManager.recreateObject(gameObject);

             }
        };

        }
    }

    private static  boolean mainHeroInJump (JLabel mainHeroLabel, JLabel npsLabel)
    {

        int mainHeroYBottom  = mainHeroLabel.getY() + mainHeroLabel.getHeight() ;
        int npsYMiddle = npsLabel.getY() + npsLabel.getHeight()/2;

        return mainHeroYBottom < npsYMiddle;

    }
}
