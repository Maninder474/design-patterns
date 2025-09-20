package com.opentext.patterns.factory.pizza.pizzaFactory;

import com.opentext.patterns.factory.pizza.ChicagoStyleCheesePizza;
import com.opentext.patterns.factory.pizza.Pizza;
import com.opentext.patterns.factory.pizza.ChicagoStyleViggePizza;
import com.opentext.patterns.factory.pizza.ChicagoStyleClamPizza;
import com.opentext.patterns.factory.pizza.ChicagoStylePepperoniPizza;

public class ChicagoPizzaStore extends PizzaStore {

    @Override
    public Pizza createPizza(String type) {
        Pizza pizza = null;
        if (type.equalsIgnoreCase("cheese")) {
            pizza = new ChicagoStyleCheesePizza();
        } else if (type.equalsIgnoreCase("veggie")) {
            pizza = new ChicagoStyleViggePizza();
        } else if (type.equalsIgnoreCase("pepperoni")) {
            pizza = new ChicagoStylePepperoniPizza();
        } else if (type.equalsIgnoreCase("clam")) {
            pizza = new ChicagoStyleClamPizza();
        }
        return pizza;
    }
}
