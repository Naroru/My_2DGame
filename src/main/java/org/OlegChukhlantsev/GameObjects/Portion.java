package org.OlegChukhlantsev.GameObjects;

import org.OlegChukhlantsev.Characters.Character;
import org.OlegChukhlantsev.Frames.Game;

import javax.swing.*;

public class Portion extends GameObject{

    private final int health;

    public Portion(ImageIcon icon) {
        super(icon);
        health = 30;
    }



    @Override
    public void effectOnHero(Character mainHero) {


        if(mainHero.getCurrent_health() < mainHero.getInitial_health()) {

            int newHealth = Math.min(mainHero.getCurrent_health() + health, mainHero.getInitial_health());
            mainHero.setCurrent_health(newHealth);

        }
    }
}
