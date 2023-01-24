package org.OlegChukhlantsev.Enums;

public enum ActionTypes {

    MOVE,
    FIGHT,
    JUMP,
    STAY,
    DIE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
