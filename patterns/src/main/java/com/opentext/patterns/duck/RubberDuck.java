package com.opentext.patterns.duck;

import com.opentext.patterns.duck.behaviour.FlyNoFly;
import com.opentext.patterns.duck.behaviour.Quack;

public class RubberDuck extends Duck{

    public void display() {
        System.out.println("I'm a rubber duck");
    }
}
