package com.sgrep.proc;
import java.lang.Character;

import java.util.*;
import java.util.ArrayList;
public class Grepy{
   public ArrayList<Transition> NSTrans; // nfa states
   public ArrayList<Transition> DSTrans; //dfa states
    int stateCounter = 0;

    public Grepy(){
        this.NSTrans = new ArrayList<Transition>();
        this.DSTrans = new ArrayList<Transition>();

        System.out.println("Grepy Initialized");
    }
    






    public ArrayList<String> pIn( String str) {
        char[] chAr = str.toCharArray();
        ArrayList<String> res = new ArrayList<String>();
        ArrayList<Integer> lParens = new ArrayList<Integer>();
        ArrayList<String> groups = new ArrayList<String>();
        ArrayList<Integer> choices = new ArrayList<Integer>();
        ArrayList<Integer> loops = new ArrayList<Integer>();

        if (str.indexOf(')') != -1) {
            for (int i = 0; i < chAr.length; i++) {
                if (chAr[i] == '(') {
                //    System.out.println("found a (");
                    lParens.add(i);
                } else if (chAr[i] == ')') {
                 //   System.out.println("found a )");
                    int lastInd = lParens.get(lParens.size() - 1);
                   // System.out.println("Left Parens " + lParens);
                    String group = Integer.toString(lastInd) + '-' + Integer.toString(i + 1);
                    groups.add(group);
                 //   System.out.println("Pop");
                    lParens.remove(lParens.size() - 1);
                } else if (chAr[i] == '+') {
               //     System.out.println("found a +");
                    choices.add(i);
                } else if (chAr[i] == '*') {
                 //   System.out.println("found a *");
                    if (chAr[(i - 1)] == ')') {
                        for (int x = 0;x<groups.size();x++) {
                        //    System.out.println("in loop");
                            //  console.log("Groups "+ groups[n]);
                            //   console.log( "end" + groups[n].substr(groups[n].indexOf('-')+1) + ',' + (i+1));
                            String g = groups.get(x);
                            int pos = g.indexOf('-');

                            String sGroup = g.substring(pos + 1);
                            int end = Integer.parseInt(sGroup);
                            if (end == i) {

                            //    System.out.println("Found a match.");
//                                System.out.println(Integer.parseInt((groups.get(n).substring(0, groups.get(n).indexOf('-') + 1))) + i);
                                groups.set(x, g.substring(0, g.indexOf('-') + 1) + (i+1));

                            }
                        }
                    }else{
                        int start = 0;
                        for(int n = i;n>=0;n-- ){
                            if(chAr[n] == ')' || chAr[n] == '+'){
                                start = n;
                                break;
                            }
                        }
                        String s = Integer.toString(start+1) +'-'+ Integer.toString(i+1);
                        groups.add(s);
                    }
                }

            }
        }

        for(String group : groups){
            int lBound = Integer.parseInt(group.substring(0,group.indexOf('-')));
            int rBound = Integer.parseInt(group.substring(group.indexOf('-')+1));
            String g = str.substring(lBound,rBound);


            res.add(g);
        }
        return res;


    }

    public boolean mProc(String str){
       boolean valid = false;
       ArrayList<String> parts = pIn(str);
       ArrayList<Character> alphabet = pAlpha(str);
        ArrayList<ArrayList<Transition>> nSegments = new ArrayList<ArrayList<Transition>>();
        ArrayList<State> nStates = new ArrayList<State>();
        ArrayList<ArrayList<Transition>> dSegmentts = new ArrayList<ArrayList<Transition>>();
        ArrayList<State> dStates = new ArrayList<State>();

       for(String part : parts){
           if(part.indexOf('(') == part.lastIndexOf('(')){
               nSegments.add(new ArrayList<Transition>());
               nPartParser(nSegments.get(nSegments.size()-1), nStates, part);
               for(Transition t : nSegments.get(nSegments.size()-1)){
                   System.out.println("from " + t.getfState().getId() + " | to " + t.gettState().getId()+ " by means of " + t.c);
               }
               //dPartParser(dParts, part);
           }
       }


        return valid;
    }

    private ArrayList<Character> pAlpha(String str) {
        char[] full = str.toCharArray();
        ArrayList<Character> alpha = new ArrayList<Character>();
        for (char c : full) {
            if (!alpha.contains(c)) {
                alpha.add(c);
            }
            ArrayList<Character> bad = new ArrayList<Character>();
            bad.add('(');
            bad.add(')');
            bad.add('+');
            bad.add('*');


            alpha.removeAll(bad);

        }
        return alpha;
    }

    public void nPartParser(ArrayList<Transition> section, ArrayList<State> nStates, String str){
        ArrayList<Character> subAlpha = pAlpha(str);
       if(str.indexOf('(') >=0){
           if(str.indexOf('*') >= 0){
               if(str.indexOf('+')>= 0){
                  n_cLoop(section, nStates, str, subAlpha);  //choice loop
               }else{
                   n_pLoop(section, nStates,str, subAlpha); //pattern loop
               }
           }else if(str.indexOf('+') >=0){
               n_choice(section, nStates,str, subAlpha);    //simple choice
           }
       }else{
           if(str.indexOf('+')>=0){
               n_choice(section,nStates, str, subAlpha);       //simple choice
           }else if(str.indexOf('*')>= 0){
               n_repeat(section,nStates, str, subAlpha);        //repeat of one character
           }else{
               n_sChar(section,nStates, str, subAlpha);         //single character
           }
       }
    }



    private void n_cLoop(ArrayList<Transition> section, ArrayList<State> nStates, String str, ArrayList<Character> subAlpha) {

        State s = new State(stateCounter++, State.Type.ACCEPT);
        s.setStart(true);
        nStates.add(s);
        for(char c : subAlpha){
           section.add(new Transition(s,s,c));
        }
    }

    private void n_pLoop(ArrayList<Transition> section, ArrayList<State> nStates, String str, ArrayList<Character> subAlpha) {

        State s = new State(stateCounter++, State.Type.ACCEPT);
        s.setStart(true);
        nStates.add(s);
        State prev = s;
        String loop = str.substring(str.indexOf('(')+1, str.indexOf(')'));
        if(loop.length() > 1){
            for(int i = 0;i<loop.length();i++) {
                if(i == loop.length()-1){
                    section.add(new Transition(prev, s, loop.charAt(i)));
                }else{
                    State st = new State(stateCounter++, State.Type.INTER);
                    nStates.add(st);
                    section.add(new Transition(prev, st, loop.charAt(i)));
                    prev = st;
                }

            }

        }
    }
    private void n_choice(ArrayList<Transition> section, ArrayList<State> nStates, String str, ArrayList<Character> subAlpha) {

       State s = new State(stateCounter++, State.Type.INTER);
       s.setStart(true);
       nStates.add(s);
       State next = new State(stateCounter++, State.Type.ACCEPT);
       nStates.add(next);
       for(char c : subAlpha){
           section.add(new Transition(s,next, c));
       }
    }
    private void n_repeat(ArrayList<Transition> section, ArrayList<State> nStates, String str, ArrayList<Character> subAlpha) {

       State s = new State(stateCounter++, State.Type.ACCEPT);
       s.setStart(true);
       nStates.add(s);
       for(char c : subAlpha){
           section.add(new Transition(s,s,c));
       }
    }

    private void n_sChar(ArrayList<Transition> section, ArrayList<State> nStates, String str, ArrayList<Character> subAlpha) {
       State s = new State(stateCounter++, State.Type.INTER);
       s.setStart(true);
       nStates.add(s);
       State prev = s;
        for(char c : subAlpha){
            State next = new State(stateCounter++, State.Type.INTER);
            nStates.add(next);
            section.add(new Transition(prev,next,c));
            prev = next;
        }
        nStates.get(nStates.size()-1).setType('A');
    }



}
