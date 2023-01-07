package Characters;
import Characters.Character;
import Enums.CharacterOrientations;
import Managers.ThreadsWaiting;

public class ManagerPlayerAction {

    private volatile boolean rightMovingButtonActive;
    private volatile boolean leftMovingButtonActive;
    private volatile boolean jumpButtonActive;
    private volatile boolean fightButtonActive;

    private Character mainHero;

    public void startManagePlayerAction() {
       /* Thread playerActionThread = new Thread(() ->
        {
            Character.CharacterLabel mainHeroLabel = mainHero.getCharacterLabel();

            updateMainHeroState(mainHero);

            while (true) {

                if (rightMovingButtonActive || leftMovingButtonActive) {

                    if (rightMovingButtonActive && !leftMovingButtonActive)
                        mainHeroLabel.setCharacterOrientations(CharacterOrientations.RIGHT);
                    else if (!rightMovingButtonActive)
                        mainHeroLabel.setCharacterOrientations(CharacterOrientations.LEFT);

                    Thread movingThread = mainHero.move();
                    ThreadsWaiting.wait(movingThread);

                }

                if (jumpButtonActive)
                    mainHero.jump();
                else
                    mainHero.setJumping(false);

                if (fightButtonActive)
                    mainHero.fight();
                else
                    mainHero.setFighting(false);

                if (!mainHero.isMoving() && !mainHero.isFighting() && !mainHero.isJumping())
                    mainHero.stay();

                ThreadsWaiting.wait(100);
            }
        });

        playerActionThread.start();*/
    }
    public ManagerPlayerAction() {

        rightMovingButtonActive = false;
        leftMovingButtonActive = false;
        jumpButtonActive = false;
        fightButtonActive = false;
    }

    public void setMainHero(Character mainHero) {
        this.mainHero = mainHero;
    }

    private void updateMainHeroState(Character mainHero)
    {
        Thread updateStateThread = new Thread(() ->
        {
            while (true) {
                if (!rightMovingButtonActive && !leftMovingButtonActive)
                    mainHero.setMoving(false);

                if (!jumpButtonActive)
                    mainHero.setJumping(false);

                if (!fightButtonActive)
                    mainHero.setFighting(false);

                ThreadsWaiting.wait(10);
            }
        });

        updateStateThread.start();

    }

    public void setRightMovingButtonActive(boolean rightMovingButtonActive) {

        this.rightMovingButtonActive = rightMovingButtonActive;

        if (!rightMovingButtonActive)
            mainHero.setMoving(false);
    }

    public void setLeftMovingButtonActive(boolean leftMovingButtonActive) {
        this.leftMovingButtonActive = leftMovingButtonActive;
    }

    public void setJumpButtonActive(boolean jumpButtonActive) {
        this.jumpButtonActive = jumpButtonActive;
    }

    public void setFightButtonActive(boolean fightButtonActive) {
        this.fightButtonActive = fightButtonActive;
    }
}
