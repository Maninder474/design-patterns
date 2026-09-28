package com.opentext.patterns.duck.behaviour;

public class MuteQuack implements Quackable{
    @Override
    public void quack() {
        System.out.println("I cannot quack");
    }
}
