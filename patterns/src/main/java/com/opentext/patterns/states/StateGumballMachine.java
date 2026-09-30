package com.opentext.patterns.states;

public class StateGumballMachine {
    State SOLD_OUT;
    State NO_QUARTER;
    State HAS_QUARTER;
    State SOLD;
    State WINNER;

    State state = SOLD_OUT;
    int count = 0;

    public StateGumballMachine(int numberGumballs) {
        SOLD_OUT = new SoldOutState(this);
        HAS_QUARTER = new HasQuarterState(this);
        SOLD = new SoldState(this);
        NO_QUARTER = new NoQuarterState(this);
        WINNER = new WinnerState(this);
        this.count = numberGumballs;
        if (numberGumballs > 0) {
            state = NO_QUARTER;
        }
    }
    public void insertQuarter() {
        state.insertQuarter();
    }
    public void ejectQuarter(){
        state.ejectQuarter();
    }
    public void turnCrank(){
        state.turnCrank();
        state.dispense();
    }
    void setState(State state){
        this.state = state;
    }
    void releaseBall(){
        System.out.println("A gumball comes rolling out the slot...");
        if(count!=0){
            count--;
        }
    }

    public String toString(){
        String stateValue = "";
        if(state == NO_QUARTER){
            stateValue = "NO_QUARTER";
        }else if(state == HAS_QUARTER){
            stateValue = "Has_QUARTER";
        }else if (state == SOLD){
            stateValue = "SOLD";
        }else if(state == SOLD_OUT){
            stateValue = "SOLD_OUT";
        }
        return "State of the Machine is "+stateValue;
    }
    public State getSoldOutState() {
        return SOLD_OUT;
    }
    public State getNoQuarterState() {
        return NO_QUARTER;
    }
    public State getHasQuarterState() {
        return HAS_QUARTER;
    }
    public State getSoldState() {
        return SOLD;
    }
    public void refill(int count) {
        this.count += count;
        System.out.println("The gumball machine was just refilled; it's new count is: " + this.count);
        state = NO_QUARTER;
    }
    public State getState() {
        return state;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }
    public void setSoldOutState(State soldOutState) {
        this.SOLD_OUT = soldOutState;
    }
    public void setNoQuarterState(State noQuarterState) {
        this.NO_QUARTER = noQuarterState;
    }
    public void setHasQuarterState(State hasQuarterState) {
        this.HAS_QUARTER = hasQuarterState;
    }
    public void setSoldState(State soldState) {
        this.SOLD = soldState;
    }
    public void setWinnerState(State winnerState) {
        this.WINNER = winnerState;
    }
    public State getWinnerState() {
        return WINNER;
    }
}
