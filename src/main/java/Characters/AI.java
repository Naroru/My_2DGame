package Characters;

import Frames.GameField;

public class AI {

    static Thread thread;

    private static boolean needMoving = false;
    private static boolean needFighting = false;

    public static  void wait(int millisec)
    {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
        public static  void npsAIStart(Character nps)
        {
            updateNPSState(nps);

            Thread AI = new Thread(() -> {
                while (true) {

                    wait(200);


                    if (needMoving) {
                        Thread moveThread = nps.move();
                        try {
                            moveThread.join();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if(needFighting) {
                        Thread fightThread = nps.fight();
                        try {
                            fightThread.join();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });

            AI.start();
        }

        private static void updateNPSState(Character nps){

            Thread threadStateNPSDefinition = new Thread(() -> {

                Character mainHero = GameField.getMainHero();

                while (true) {

                    if (mainHero.getX() < nps.getX()) {

                        int mainHeroRightBorderX = mainHero.getX() + mainHero.getIcon().getIconWidth();

                        if (mainHeroRightBorderX >= nps.getX())
                        {
                            needFighting = true;
                            needMoving = false;
                            nps.setMoving(false);
                        }
                        else {
                            needFighting = false;
                            needMoving = true;
                            nps.setFighting(false);
                            nps.setRightOrientation(false);
                        }

                    } else if (mainHero.getX() > nps.getX()) {
                        int npsRightBorderX = nps.getX() + nps.getIcon().getIconWidth();
                        int mainHeroX = mainHero.getX();

                        if (mainHeroX <= npsRightBorderX)
                        {
                            needFighting = true;
                            needMoving = false;
                            nps.setMoving(false);

                        } else {
                            needFighting = false;
                            needMoving = true;
                            nps.setFighting(false);
                            nps.setRightOrientation(true);
                        }

                    }

                 wait(10);
                }

            });
            threadStateNPSDefinition.start();

        }

    }


