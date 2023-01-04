package Managers;
import Characters.Character;
import Enums.CharacterOrientations;

public class ManagerPlayerAction {

    private boolean rightMovingButtonActive;
    private boolean leftMovingButtonActive;
    private boolean jumpButtonActive;
    private boolean fightButtonActive;

    public void startManagePlayerAction(Character mainHero) {
        Thread thread = new Thread(() ->
        {
            Character.CharacterLabel mainHeroLabel = mainHero.getCharacterLabel();

            while (true) {

                if(rightMovingButtonActive && !leftMovingButtonActive)
                {
                    mainHeroLabel.setCharacterOrientations(CharacterOrientations.RIGHT);
                    mainHero.move();
                }

                else if(!rightMovingButtonActive && leftMovingButtonActive)
                {
                    mainHeroLabel.setCharacterOrientations(CharacterOrientations.LEFT);
                    mainHero.move();
                }

                else if (jumpButtonActive)
                    mainHero.jump();

                else if (fightButtonActive)
                    mainHero.fight();

                else
                    mainHero.stay();

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }
    public ManagerPlayerAction() {

        rightMovingButtonActive = false;
        leftMovingButtonActive = false;
        jumpButtonActive = false;
        fightButtonActive = false;

    }


    public void setRightMovingButtonActive(boolean rightMovingButtonActive) {
        this.rightMovingButtonActive = rightMovingButtonActive;
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
