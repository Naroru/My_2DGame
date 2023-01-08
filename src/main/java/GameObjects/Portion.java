package GameObjects;

import Characters.Character;

import javax.swing.*;
import java.util.List;

public class Portion extends GameObject{

    private int health;

    public Portion(ImageIcon icon) {
        super(icon);
        health = 15;
    }



    @Override
    public void effectOnHero(Character mainHero) {

    }
}
