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
        ACCEPT,
        ERROR
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
                System.out.println("WARNING: " + getId() + " WAS SET TO ACCEPT");
                break;
            case 'E':
                this.stateType = Type.ERROR;
                break;
        }
    }

    public Type getType(){
        return this.stateType;
    }
    public String explain(){
            StringBuilder out = new StringBuilder();

            if(isStart){
                out.append("Start State ");
            }

            if(this.stateType == Type.INTER){
               out.append("Intermediate State ").append(getId());
            }else if(this.stateType == Type.ACCEPT){
                out.append("Accept State ").append(getId());
            }else if(this.stateType == Type.ERROR){
               return ("Error State " + getId());
            }
            return out.toString();



    }

}