package com.sgrep.proc;
import java.lang.Character;

import java.util.*;

public class Grepy{
   public ArrayList<HashMap<Character,State>> NSTrans; // nfa states
   public ArrayList<HashMap<Character,State>> DSTrans; //dfa states

    
   public Grepy(){
        this.NSTrans = new ArrayList<HashMap<Character,State>>();
        this.DSTrans = new ArrayList<HashMap<Character, State>>();

        System.out.println("Grepy Initialized");
    }
    
//    public ArrayList<Object> parseInput(String rawReg){
//        ArrayList<Character> alphabet = new ArrayList<Character>();
//        boolean parsing = true;
//        ArrayList<String> subExpr = new ArrayList<String>();
//        ArrayList<Integer> lPer = new ArrayList<Integer>();
//        ArrayList<Integer> rPer = new ArrayList<Integer>();
//        ArrayList<Integer> plus = new ArrayList<Integer>();
//        ArrayList<Integer> star = new ArrayList<Integer>();
//        for(int i = 0;i<rawReg.length();i++){
//            char ch = rawReg.charAt(i);
//            switch(ch){
//                case '(':
//                    lPer.add(i);
//                    break;
//                case ')':
//                    rPer.add(i);
//                    break;
//                case '+':
//                    plus.add(i);
//                    break;
//                case '*':
//                    star.add(i);
//                    break;
//                default:
//                    if(!alphabet.contains(rawReg.charAt(i)){
//                    alphabet.add(rawReg.charAt(i));
//                }
//                }
//        }
//        int c_lPer = 0;
//        int c_rPer = 0;
//        int c_plus = 0;
//        int c_star = 0;
//        char c_char = '';
//        int index = 0;
//        c_rPer = rPer.get(0);
//        index = c_rPer - 1;
//        while(c_char != '('){
//            c_char = rawReg.charAt(index);
//            index--;
//        }
//        if(index != lPer.get(0)){   // we know this is a nested thing
//            //mark this in the arraylist as a sub expression, to be rechecked later
//        }
//
//
//    }

    public TStack preProc(String rawReg){
       TStack stack = new TStack();
       char[] chArr = rawReg.toCharArray();

        for(char ch : chArr){
            stack.push(ch);
        }
        return stack;
    }

//    public ArrayList<String> parser(String str){
//       ArrayList<String> cGroups = new ArrayList<String>();
//       cGroup = pIn(cGroups, str);
//       for(String group : cGroups){
//
//       }
//
//
//    }

    public ArrayList<String> pIn(ArrayList<String> cGroups, String str) {
        char[] chAr = str.toCharArray();
        ArrayList<String> res = new ArrayList<String>();
        ArrayList<Integer> lParens = new ArrayList<Integer>();
        ArrayList<String> groups = new ArrayList<String>();
        ArrayList<Integer> choices = new ArrayList<Integer>();
        ArrayList<Integer> loops = new ArrayList<Integer>();

        if (str.indexOf(')') != -1) {
            for (int i = 0; i < chAr.length; i++) {
                if (chAr[i] == '(') {
                    System.out.println("found a (");
                    lParens.add(i);
                } else if (chAr[i] == ')') {
                    System.out.println("found a )");
                    int lastInd = lParens.get(lParens.size() - 1);
                    System.out.println("Left Parens " + lParens);
                    String group = Integer.toString(lastInd) + '-' + Integer.toString(i + 1);
                    groups.add(group);
                    System.out.println("Pop");
                    lParens.remove(lParens.size() - 1);
                } else if (chAr[i] == '+') {
                    System.out.println("found a +");
                    choices.add(i);
                } else if (chAr[i] == '*') {
                    System.out.println("found a *");
                    if (chAr[(i - 1)] == ')') {
                        for (int x = 0;x<groups.size();x++) {
                            System.out.println("in loop");
                            //  console.log("Groups "+ groups[n]);
                            //   console.log( "end" + groups[n].substr(groups[n].indexOf('-')+1) + ',' + (i+1));
                            String g = groups.get(x);
                            int pos = g.indexOf('-');

                            String sGroup = g.substring(pos + 1);
                            int end = Integer.parseInt(sGroup);
                            if (end == i) {

                                System.out.println("Found a match.");
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

            res.add(str.substring(lBound,rBound));
        }
        return res;


    }


//
//        TNode cNode = stack.top;
//        boolean processing = true;
//        TNode lMark;
//        TNode rMark;
//
//        while(processing){
//            if(cNode.data instanceof char){
//                if(
//            }
//
//        }
            //return res;
//       }




    public int t1(String s){
        if(s.equals("hi")){
            return 8;
        }else{
            return 7;
        }
    }
}
