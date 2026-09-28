package com.opentext.patterns.duck;

public class TurkeyAdapter extends Duck {
    private Turkey turkey;

    public TurkeyAdapter(Turkey turkey) {
        this.turkey = turkey;
    }

    @Override
    public void display() {
        turkey.gobble();
        turkey.fly();
    }
}
