package com.opentext.patterns.decorator.starbuzz;

public class DarkRoast extends Beverage{

    public DarkRoast() {
        description = "Dark Roast Coffee";
    }

    @Override
    public float getCost() {
        return 20.25f;
    }
}
