package com.opentext.patterns.factory.pizza;

import java.util.ArrayList;

public class Pizza {
    public String name="normal pizza";
    public String dough="thin dough";
    public String sauce="normal sauce";
    ArrayList<String> toppings=new ArrayList<>();

    public void prepare() {
        System.out.println("Preparing "+name);
        System.out.println("Tossing dough...");
        System.out.println("Adding sauce...");
        for (String o : toppings) {
            System.out.println("  " + o);
        }
    }
    public void bake() {
        System.out.println("Baking for 25 minutes at 350 ");
    }
    public void cut() {
        System.out.println("Cutting the pizza into diagonal slices");
    }
    public void box() {
        System.out.println("Placing the pizza in official pizzaStore box");
    }

}
