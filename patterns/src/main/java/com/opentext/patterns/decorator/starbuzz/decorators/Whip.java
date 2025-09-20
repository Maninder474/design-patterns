package com.opentext.patterns.decorator.starbuzz.decorators;

import com.opentext.patterns.decorator.starbuzz.Beverage;

public class Whip extends CondimentsDecorator{

    Beverage beverage;
    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }
    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Whip";
    }

    @Override
    public float getCost() {
        return beverage.getCost() + 2.5f;
    }
}
