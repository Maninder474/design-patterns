package com.opentext.patterns.decorator.starbuzz;

public class HouseBlend extends Beverage{
    public HouseBlend() {
        description = "House Blend Coffee";
    }

    @Override
    public float getCost() {
        return 10.25f;
    }

}
