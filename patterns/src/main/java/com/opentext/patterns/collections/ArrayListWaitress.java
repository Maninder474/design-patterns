package com.opentext.patterns.collections;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayListWaitress {
    ArrayList<Menu> menu;

    public ArrayListWaitress(ArrayList menus){
        this.menu = menus;
    }
    public void printMenuArrayList(){
        Iterator<Menu> menuIterator = menu.iterator();
        while(menuIterator.hasNext()){
            Menu menuItem = menuIterator.next();
            printMenu(menuItem.createIterator());
        }
    }
    private void printMenu(Iterator iterator){
        while(iterator.hasNext()){
            MenuItem menuItem = (MenuItem)iterator.next();
            System.out.print(menuItem.getName()+", ");
            System.out.print(menuItem.getPrice()+" -- ");
            System.out.println(menuItem.getDescription());
        }
    }
}
