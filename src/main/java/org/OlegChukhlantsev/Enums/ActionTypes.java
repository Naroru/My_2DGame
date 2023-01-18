package org.OlegChukhlantsev.Enums;

public enum ActionTypes {

    MOVE,
    FIGHT,
    JUMP,
    STAY;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
