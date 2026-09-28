package com.opentext.patterns.factory.pizza.ingredientFactory;

public interface PizzaIngredientFactory {

    public String createDough();
    public String createSauce();
    public String createTopping();
}
