package com.opentext.patterns.command.items;

public class GarageDoor {
    boolean isDoorOpen;

    public GarageDoor() {
        isDoorOpen = false;
    }
    public void openDoor() {
        isDoorOpen = true;
        System.out.println("Door is open");
    }
}
