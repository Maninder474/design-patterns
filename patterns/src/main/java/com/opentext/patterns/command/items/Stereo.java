package com.opentext.patterns.command.items;

public class Stereo {
    private String stereoName;
    public Stereo(String stereoName) {
        this.stereoName = stereoName;
    }
    public void on(){
        System.out.println("Stereo on");
    }
    public void off(){
        System.out.println("Stereo off");
    }
    public void setVolume(double volume) {
        System.out.println("Stereo set volume: " + volume);
    }
    public void setCD(){
        System.out.println("Stereo set CD");
    }
}
