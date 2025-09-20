package com.opentext.patterns.duck.behaviour;

public class FlyWithRocket implements Flyable{
    @Override
    public void fly() {
        System.out.println("I am Flying with Rocket");
    }
}
