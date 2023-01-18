package org.OlegChukhlantsev.Characters;

import org.OlegChukhlantsev.CommonManagers.IconManager;
import org.OlegChukhlantsev.Enums.CharacterOrientations;
import org.OlegChukhlantsev.CommonManagers.ThreadsWaiting;
import org.OlegChukhlantsev.Frames.Game;
import org.OlegChukhlantsev.Icons.CharacterIcon;

public class AI {

    private static boolean needMoving = false;
    private static boolean needFighting = false;
    public static Game game;

        public static  void npsAIStart()
        {
            Character nps = game.getNPS();
            Character mainHero = game.getMainHero();

            updateNPSState(nps, mainHero);

            Thread AI = new Thread(() -> {
                while (!game.isFinish()) {

                    ThreadsWaiting.wait(200);

                    if (needMoving) {
                        Thread moveThread = nps.move();
                        ThreadsWaiting.wait(moveThread);
                    }

                    if (needFighting) {
                        Thread fightThread = nps.fight();
                        ThreadsWaiting.wait(fightThread);
                    }
                }
            });

            AI.start();
        }

        private static void updateNPSState(Character nps, Character mainHero){

                Thread threadStateNPSDefinition = new Thread(() -> {

                Character.CharacterLabel npsLabel = nps.getCharacterLabel();
                Character.CharacterLabel mainHeroLabel = mainHero.getCharacterLabel();

                while (true) {

                    int nps_xCoordinate =  npsLabel.getX();
                    int mainHero_xCoordinate = mainHeroLabel.getX();

                    if (mainHero_xCoordinate < nps_xCoordinate) {


                        if(InteractionManager.characterAchieveEnemyBody(nps,mainHero))
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

                        if(InteractionManager.characterAchieveEnemyBody(nps,mainHero))
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

                    ThreadsWaiting.wait(10);
                }

            });
            threadStateNPSDefinition.start();

        }

    }


