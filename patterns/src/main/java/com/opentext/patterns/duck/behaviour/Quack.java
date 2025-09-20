package com.opentext.patterns.duck.behaviour;

public class Quack implements  Quackable{

    @Override
    public void quack() {
        System.out.println("Quack Quack");
    }
}
