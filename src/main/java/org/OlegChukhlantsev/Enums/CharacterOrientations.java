package org.OlegChukhlantsev.Enums;

public enum CharacterOrientations {
    LEFT,
    RIGHT;

    @Override
    public String toString() {
        return  this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
    }
}
