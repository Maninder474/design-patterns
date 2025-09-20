package com.opentext.patterns.command.items;

public class Light {
    boolean isLightOn;
    public Light() {
        isLightOn = false;
    }

    public void on(){
        isLightOn = true;
        System.out.println("Light on");
    }
    public void off(){
        isLightOn = false;
        System.out.println("Light off");
    }
}
