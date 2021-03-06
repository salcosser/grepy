package com.sgrep.proc;

public class Transition { // used to hold and maintain transitions
    public State getfState() {
        return fState;
    }

    public void setfState(State fState) {
        this.fState = fState;
    }

    State fState;

    public State gettState() {
        return tState;
    }

    public void settState(State tState) {
        this.tState = tState;
    }

    State tState;

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    char c;

    public Transition(State fState, State tState, char c) {
        this.fState = fState;
        this.tState = tState;
        this.c = c;
        //System.out.println("Transition from " + this.fState.getId() + " to "+ this.tState.getId()+ " my means of " + this.c);
    }
    public  void  explain(){ // used mainly for debugging
        System.out.println("Transition from " + this.fState.explain() + " to "+ this.tState.explain() + " my means of " + this.c);
    }
}
