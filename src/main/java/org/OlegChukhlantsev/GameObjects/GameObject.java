package org.OlegChukhlantsev.GameObjects;

import org.OlegChukhlantsev.Characters.Character;

import javax.swing.*;

public  abstract  class GameObject extends JLabel {


    public GameObject (ImageIcon icon)
    {
        setIcon(icon);
    }


    public abstract void effectOnHero(Character mainHero);



}
