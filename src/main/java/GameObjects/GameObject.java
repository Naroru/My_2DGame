package GameObjects;

import Characters.Character;
import Frames.Game;

import javax.swing.*;
import java.util.List;

public  abstract  class GameObject extends JLabel {

    public GameObject (ImageIcon icon)
    {
        setIcon(icon);
    }


    public abstract void effectOnHero(Character mainHero);



}
