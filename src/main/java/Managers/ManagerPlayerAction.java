package Managers;

public class ManagerPlayerAction {

    private Character mainHero;

    private boolean rightMovingButtonActive;
    private boolean leftMovingButtonActive;
    private boolean jumpButtonActive;
    private boolean fightButtonActive;

    public void startManagePlayerAction()
    {

    }

    public ManagerPlayerAction(Character mainHero) {

        this.mainHero = mainHero;

        rightMovingButtonActive = false;
        leftMovingButtonActive = false;
        jumpButtonActive = false;
        fightButtonActive = false;

    }

    private void move()
    {

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
