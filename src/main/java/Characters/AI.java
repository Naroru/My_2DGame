package Characters;

import Enums.CharacterOrientations;

public class AI {

    static Thread thread;

    private static boolean needMoving = false;
    private static boolean needFighting = false;

    private static void wait(int millisec)
    {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void wait(Thread thread)
    {
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
        public static  void npsAIStart(Character nps, Character mainHero)
        {

            wait(800);

            updateNPSState(nps, mainHero);

            Thread AI = new Thread(() -> {
                while (true) {

                    wait(200);

                    if (needMoving) {
                        Thread moveThread = nps.move();
                         wait(moveThread);
                    }

                    if (needFighting) {
                        Thread fightThread = nps.fight();
                        wait(fightThread);
                    }
                }
            });

            AI.start();
        }

        private static void updateNPSState(Character nps, Character mainHero){

            Thread threadStateNPSDefinition = new Thread(() -> {

                Character.CharacterLabel npsLabel = nps.getCharacterLabel();
                Character.CharacterLabel mainHeroLabel = mainHero.getCharacterLabel();

                int nps_xCoordinate =  npsLabel.getX();
                int mainHero_xCoordinate = mainHeroLabel.getX();

                while (true) {

                    if (mainHero_xCoordinate< nps_xCoordinate) {

                        int mainHeroRightBorderX = mainHero_xCoordinate + mainHeroLabel.getIcon().getIconWidth();

                        if (mainHeroRightBorderX >= nps_xCoordinate)
                        {
                            needFighting = true;
                            needMoving = false;
                            nps.setMoving(false);
                        }
                        else {
                            needFighting = false;
                            needMoving = true;
                            nps.setFighting(false);
                            npsLabel.setCharacterOrientations(CharacterOrientations.LEFT);
                        }

                    } else if (mainHero_xCoordinate > nps_xCoordinate) {
                        int npsRightBorderX = nps_xCoordinate + npsLabel.getIcon().getIconWidth();
                        int mainHeroX = mainHero_xCoordinate;

                        if (mainHeroX <= npsRightBorderX)
                        {
                            needFighting = true;
                            needMoving = false;
                            nps.setMoving(false);

                        } else {
                            needFighting = false;
                            needMoving = true;
                            nps.setFighting(false);
                            npsLabel.setCharacterOrientations(CharacterOrientations.RIGHT);
                        }

                    }

                 wait(10);
                }

            });
            threadStateNPSDefinition.start();

        }

    }


