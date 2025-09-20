package com.opentext.patterns.factory.pizza;

public class ChicagoStyleClamPizza extends Pizza {
    public ChicagoStyleClamPizza() {
        name = "Chicago Style Clam Pizza";
        dough = "Thick Crust Dough";
        sauce = "spicy tomato Sauce";

        toppings.add("Chicago Mushroom Topping");
    }

    @Override
    public void cut() {
        System.out.println("Cutting the pizza into square slices");
    }
}
