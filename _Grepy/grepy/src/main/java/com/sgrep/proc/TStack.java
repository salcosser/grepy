package com.sgrep.proc;

public class TStack {
    public TNode top;
    public TStack(){
        this.top = null;
    }

    public boolean isEmpty(){
        return top == null;
    }

    public void push(Object obj){
        TNode n = new TNode(obj);
        n.next = top;
        top = n;
    }

    public TNode pop(){
        if(isEmpty()){
            return null;
        }
        else{
            TNode retVal = top;
            top = top.next;
            return retVal;
        }
    }
}