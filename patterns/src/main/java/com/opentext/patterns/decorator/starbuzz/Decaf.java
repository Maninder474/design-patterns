package com.opentext.patterns.decorator.starbuzz;

public class Decaf extends Beverage{
    public Decaf() {
        description = "Decaf Coffee";
    }

    @Override
    public float getCost() {
        return 30.25f;
    }
}
