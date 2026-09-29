package com.opentext.patterns.collections;

import java.util.Hashtable;
import java.util.Iterator;

public class cafeMenu implements Menu{
    Hashtable menuItems = new Hashtable();

    public cafeMenu() {
        addItems("Veggie Burger and Air Fries", "Veggie burger on a whole wheat bun, lettuce, tomato, and fries", true, 3.99);
        addItems("Soup of the day", "A cup of the soup of the day, with a side salad", false, 3.69);
        addItems("Burrito", "A large burrito, with whole pinto beans, salsa, guacamole", true, 4.29);
    }

    private void addItems(String name, String description, boolean IsVegetarian, double price) {
        MenuItem menuItem = new MenuItem(name,description,IsVegetarian,price);
        menuItems.put(menuItem.getName(),menuItem);
    }

    @Override
    public Iterator<MenuItem> createIterator() {
        return menuItems.values().iterator();
    }
}
