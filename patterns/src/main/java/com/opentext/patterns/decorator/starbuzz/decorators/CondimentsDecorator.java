package com.opentext.patterns.decorator.starbuzz.decorators;

import com.opentext.patterns.decorator.starbuzz.Beverage;

public abstract class CondimentsDecorator extends Beverage {
    public abstract String getDescription();
}
