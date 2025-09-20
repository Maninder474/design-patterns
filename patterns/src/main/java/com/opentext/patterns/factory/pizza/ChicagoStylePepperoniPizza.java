package com.opentext.patterns.factory.pizza;

public class ChicagoStylePepperoniPizza extends Pizza {
    public ChicagoStylePepperoniPizza() {
       name = "Chicago Style Pepperoni Pizza";
       dough = "Thick Crust Dough";
       sauce = "extra hot Sauce";
       toppings.add("chicago sql Pepperoni");
    }

    @Override
    public void cut() {
        System.out.println("Cutting the pizza into square slices");
    }
}
