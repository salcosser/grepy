public class State{
    private ArrayList<State> transFrom = new ArrayList<State>();
    private ArrayList<State> transTo = new ArrayList<State>();
    String id;
    enum Type{
        START,
        INTER,
        ACCEPT
    }
    private Type stateType;
    public State(String id){
        this.id = id;
        private ArrayList<State> transFrom = new ArrayList<State>();
        private ArrayList<State> transTo = new ArrayList<State>();
    }

    public void addFrom(State st){
        this.transFrom.add(st);
    }

    public State getFrom(int ind){
        return this.transFrom.get(ind);
    }


    public void addTo(State st){
        this.transTo.add(st);
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