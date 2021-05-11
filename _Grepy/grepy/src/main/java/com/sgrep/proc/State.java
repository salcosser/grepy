package com.sgrep.proc;

public class State{
    private int id;

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    enum Type{
        INTER,
        ACCEPT
    }
    private Type stateType;
    private boolean isStart = false;
    public State(int id, Type sType){
        this.id = id;
        this.stateType = sType;
    }
    public State(){}

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void setType(char t){
        switch(t){
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