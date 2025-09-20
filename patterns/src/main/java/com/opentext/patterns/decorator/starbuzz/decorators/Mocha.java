package com.opentext.patterns.decorator.starbuzz.decorators;

import com.opentext.patterns.decorator.starbuzz.Beverage;

public class Mocha extends CondimentsDecorator {
    Beverage beverage;
    public Mocha(final Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Mocha";
    }

    @Override
    public float getCost() {
        return beverage.getCost() + 0.5f;
    }
}
