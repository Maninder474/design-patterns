package com.opentext.patterns.collections;

import com.opentext.patterns.collections.Composite.MenuComponent;

import java.util.Iterator;

public class CompositeWaitress {
    MenuComponent allMenus;
    public CompositeWaitress(MenuComponent allMenus) {
        this.allMenus = allMenus;
    }
    public void printMenu() {
        allMenus.print();
    }
    public void printVegetarianMenu() {
        Iterator iterator = allMenus.createIterator();
        System.out.println("\nVEGETARIAN MENU\n----");
        while (iterator.hasNext()) {
            MenuComponent menuComponent = (MenuComponent) iterator.next();
            try {
                if (menuComponent.isVegetarian()) {
                    menuComponent.print();
                }
            } catch (UnsupportedOperationException ignored){

            }
        }
    }
}
