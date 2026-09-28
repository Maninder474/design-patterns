package com.opentext.patterns.duck.behaviour;

public class Squeak implements Quackable{
    @Override
    public void quack() {
        System.out.println("Squak Squak");
    }
}
