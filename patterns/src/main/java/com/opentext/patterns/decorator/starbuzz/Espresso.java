package com.opentext.patterns.decorator.starbuzz;

public class Espresso extends Beverage{
    public Espresso() {
        description = "Espresso";
    }

    @Override
    public float getCost() {
        return 45.25f;
    }
}
