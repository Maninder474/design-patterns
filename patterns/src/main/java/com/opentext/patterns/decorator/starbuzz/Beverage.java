package com.opentext.patterns.decorator.starbuzz;

public abstract class Beverage {
    String description;

    public String getDescription() {
        return description;
    }

    public abstract float getCost() ;
}
