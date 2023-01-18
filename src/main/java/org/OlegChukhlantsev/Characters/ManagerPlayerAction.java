package org.OlegChukhlantsev.Characters;
import org.OlegChukhlantsev.Enums.CharacterOrientations;
import org.OlegChukhlantsev.CommonManagers.ThreadsWaiting;

public class ManagerPlayerAction {

    private Character mainHero;
    private Character.CharacterLabel mainHeroLabel;
    private boolean fightAnimationIsRun = false;

    private boolean mainHeroAct()
    {
        return mainHero.isMoving() || mainHero.isFighting() || mainHero.isJumping();
    }

    public void setMainHero(Character mainHero) {
        this.mainHero = mainHero;
        this.mainHeroLabel = mainHero.getCharacterLabel();
    }

    public void moveMainHeroRight()
    {

        mainHeroLabel.setCharacterOrientations(CharacterOrientations.RIGHT);
        mainHero.move();

    }

    public void moveMainHeroLeft()
    {


        mainHeroLabel.setCharacterOrientations(CharacterOrientations.LEFT);
        mainHero.move();

    }

    public void stopMainHeroMovingRight()
    {
        if (mainHero.isMoving() && mainHeroLabel.getCharacterOrientation() == CharacterOrientations.RIGHT)
            mainHero.setMoving(false);

        if(!mainHeroAct())
            mainHero.stay();

    }

    public void stopMainHeroMovingLeft()
    {
        if (mainHero.isMoving() && mainHeroLabel.getCharacterOrientation() == CharacterOrientations.LEFT)
            mainHero.setMoving(false);

        if(!mainHeroAct())
            mainHero.stay();
    }

    public void jumpMainHero()
    {
        Thread managerJumpThread = new Thread(() ->
        {
            Thread jumpThread = mainHero.jump();
            ThreadsWaiting.wait(jumpThread);

            if (!mainHeroAct())
                mainHero.stay();
            else if(mainHero.isMoving())
                //if it was jumping with moving and moving is active, character should continue moving
                mainHero.move();
        });

        managerJumpThread.start();
    }

    public void fightMainHero()
    {
        Thread managerFightThread = new Thread(() ->
        {
            fightAnimationIsRun = true;
            Thread fightThread = mainHero.fight();
            ThreadsWaiting.wait(fightThread);
            fightAnimationIsRun = false;

            if (!mainHeroAct())
                mainHero.stay();
            else if(mainHero.isMoving())
                //if it was jumping with moving and moving is active, character should continue moving
                mainHero.move();
        });

        managerFightThread.start();

    }

    public void stopMainHeroFight()
    {
          mainHero.setFighting(false);

    }

}
