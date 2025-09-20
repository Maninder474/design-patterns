package com.opentext.patterns.command.remote;

import com.opentext.patterns.command.items.GarageDoor;

public class GarageDoorCommand implements Command{
    private GarageDoor garageDoor;
    public GarageDoorCommand(GarageDoor garageDoor) {
        this.garageDoor = garageDoor;
    }
    @Override
    public void execute() {
        garageDoor.openDoor();
    }
    public void buttonWasPressed(){
        execute();
    }


    public void undo() {}
}
