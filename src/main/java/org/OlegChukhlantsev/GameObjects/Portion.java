package org.OlegChukhlantsev.GameObjects;

import org.OlegChukhlantsev.Characters.Character;
import org.OlegChukhlantsev.Sounds.Sound;
import org.OlegChukhlantsev.CommonManagers.PropertiesManager;

import javax.swing.*;

public class Portion extends GameObject{

    private final int health;

    public Portion(ImageIcon icon) {
        super(icon);
        health = 30;
    }



    @Override
    public void effectOnHero(Character mainHero) {

        Sound.playSound(PropertiesManager.getNotNullableProperty("portionMusic"));
            int newHealth = Math.min(mainHero.getCurrent_health() + health, mainHero.getInitial_health());

            mainHero.setCurrent_health(newHealth);
    }

    @Override
    public boolean needToUse(Character mainHero) {
        return mainHero.getCurrent_health() < mainHero.getInitial_health();
    }
}
