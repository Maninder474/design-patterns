package com.opentext.patterns.factory.pizza;

public class ChicagoStyleViggePizza extends Pizza {
    public ChicagoStyleViggePizza() {
        name = "Chicago Style Vigge Pizza";
        dough = "Thick Crust Dough";
        sauce = "hot tabasco Sauce";
        toppings.add("Chicago veggies Topping");
    }

    @Override
    public void cut() {
        System.out.println("Cutting the pizza into square slices");
    }
}
