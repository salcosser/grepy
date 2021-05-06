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


    public void addTo(Charater ch, State st){
        this.transTo.put(ch, st);
    }

    public State getTo(Character ch){
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