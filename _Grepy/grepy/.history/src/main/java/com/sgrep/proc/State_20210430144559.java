package com.sgrep.proc;

import java.lang.Character;
import java.util.*;
public class State{
    private String id;
    enum Type{
        START,
        INTER,
        ACCEPT
    }
    private Type stateType;
    public State(String id, Type sType){
        this.id = id;
        this.stateType = sType;
    }

    public void setId(String id){
        this.id = id;
    }
    public String getId(){}
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