package com.opentext.patterns.duck;

import com.opentext.patterns.duck.behaviour.Flyable;
import com.opentext.patterns.duck.behaviour.Quackable;

public abstract class Duck {

    Quackable quack;
    Flyable fly;

    public void setQuack(Quackable quack) {
        this.quack = quack;
    }

    public void setFly(Flyable fly) {
        this.fly = fly;
    }

    public abstract void display();

    public void performQuack() {
        quack.quack();
    }

    public void performFly() {
        fly.fly();
    }

   public void performSwim(){
       System.out.println("Swimming!!");
   }

   public void perform(){
        display();
        performQuack();
        performFly();
        performSwim();
   }
}
