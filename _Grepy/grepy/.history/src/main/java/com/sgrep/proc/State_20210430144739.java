package com.sgrep.proc;

import java.lang.Character;
import java.util.*;
public class State{
    private int id;
    enum Type{
        START,
        INTER,
        ACCEPT
    }
    private Type stateType;
    public State(int id, Type sType){
        this.id = id;
        this.stateType = sType;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void setType(char t){
        switch(t){
            case 'S':
                this.stateType = Type.START;
                break;
            case 'I':
                this.stateType = Type.INTER;
                break;
            case 'A':
                this.stateType = Type.ACCEPT;
                break;
        }
    }

    public Type getType(){
        return this.stateType;
    }

}