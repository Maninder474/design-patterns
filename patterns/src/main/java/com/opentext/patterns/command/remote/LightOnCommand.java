package com.opentext.patterns.command.remote;

import com.opentext.patterns.command.items.Light;

public class LightOnCommand implements Command {

    private Light light;
    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }
}
