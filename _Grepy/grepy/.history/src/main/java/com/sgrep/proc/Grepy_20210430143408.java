package com.sgrep.Grepy;


import java.util.*;

public class Grepy{
    ArrayList<State> NfaStates;
    ArrayList<State> DfaStates;
   p HashMap<State,HashMap<Object,Object>> transitions;
    public Grepy(){
        this.NfaStates = new ArrayList<State>();
        this.DfaStates = new ArrayList<State>();
        this.transitions = new HashMap<State,HashMap<Object,Object>>();
        System.out.println("Grepy Initialized");
    }
    
    public ArrayList<Object> parseAlpha(String rawReg){
        ArrayList<Object> alphabet = new ArrayList<Object>();

        for(int i = 0;i<rawReg.length();i++){
            System.out.println(rawReg.charAt(i));
        }
        return alphabet;
    }
    public static int t1(String s){
        if(s == "hi"){
            return 8;
        }else{
            return 7;
        }
    }
}
