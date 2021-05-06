import java.util.*;
public class State{
    // private HashMap<State, Character> transFrom = new HashMap<State, Character>();
    private HashMap<Character, State> transitions = new HashMap<Character, State>();
    String id;
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

    // public void addFrom(State st, Character ch){
    //     this.transFrom.put(st, ch);
    // }

    // public State getFrom(int ind){
    //     return this.transFrom.get(ind);
    // }


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