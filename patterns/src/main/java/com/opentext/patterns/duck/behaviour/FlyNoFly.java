package com.opentext.patterns.duck.behaviour;

public class FlyNoFly implements Flyable{
    public void fly() {
        System.out.println("I cannot fly");
    }
}
