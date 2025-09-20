package com.opentext.patterns.command.remote;

import java.util.Arrays;

public class RemoteControl {
    Command[] onCommands;

    public RemoteControl() {
        onCommands = new Command[7];
        Command noCommand = new NoCommand();
        Arrays.fill(onCommands, noCommand);
    }

    public void setCommand(int index, Command command) {
        onCommands[index] = command;
    }

    public void buttonWasPressed(int button) {
        onCommands[button].execute();
    }

    @Override
    public String toString() {
        return "RemoteControl{" +
                "onCommands=" + Arrays.toString(onCommands) +
                '}';
    }
}
