import java.util.*;
public class State{
    private HashMap<State, Character> transFrom = new HashMap<State, Character>();
    private HashMap<State, Character> transTo = new HashMap<State, Character>();
    String id;
    enum Type{
        START,
        INTER,
        ACCEPT
    }
    private Type stateType;
    public State(String id){
        this.id = id;
        
    }

    public void addFrom(State st){
        this.transFrom.put(st);
    }

    public State getFrom(int ind){
        return this.transFrom.get(ind);
    }


    public void addTo(State st, Character ch){
        this.transTo.put(st, ch);
    }

    public State getTo(int ind){
        return this.transTo.get(ind);
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