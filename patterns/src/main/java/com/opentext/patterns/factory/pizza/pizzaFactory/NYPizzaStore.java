package com.opentext.patterns.factory.pizza.pizzaFactory;

import com.opentext.patterns.factory.pizza.NYStyleCheesePizza;
import com.opentext.patterns.factory.pizza.NYStyleClamPizza;
import com.opentext.patterns.factory.pizza.NYStylePepperoniPizza;
import com.opentext.patterns.factory.pizza.NYStyleViggePizza;
import com.opentext.patterns.factory.pizza.Pizza;

public class NYPizzaStore extends PizzaStore {

    @Override
    public Pizza createPizza(String type) {
        Pizza pizza = null;
        if (type.equalsIgnoreCase("cheese")) {
            pizza = new NYStyleCheesePizza();
        } else if (type.equalsIgnoreCase("veggie")) {
            pizza = new NYStyleViggePizza();
        } else if (type.equalsIgnoreCase("pepperoni")) {
            pizza = new NYStylePepperoniPizza();
        } else if (type.equalsIgnoreCase("clam")) {
            pizza = new NYStyleClamPizza();
        }
        return pizza;
    }
}
