import java.util.*;
public class State{
    private HashMap<char, State> transitions = new HashMap<char, State>();
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


    public void addTo(Charater ch, State st){
        this.transitions.put(ch, st);
    }

    public State getTo(Character ch){
        return this.transitions.get(ch);
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