package com.sgrep.proc;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.lang.Character;

import java.util.*;
import java.util.ArrayList;
public class Grepy{
   public ArrayList<Transition> NSTrans; // nfa states
   public ArrayList<Transition> DSTrans; //dfa states
    ArrayList<Character> alphabet;
    State nStart = new State();
    State dStart = new State();
    ArrayList<State> nAccepts = new ArrayList<State>();
    State dAccept = new State();
    int stateCounter = 0;
    int dStateCounter = 0;
    ArrayList<ArrayList<Transition>> nSegments = new ArrayList<ArrayList<Transition>>();
    ArrayList<State> nStates = new ArrayList<State>();
    ArrayList<ArrayList<Transition>> dSegments = new ArrayList<ArrayList<Transition>>();
    ArrayList<State> dStates = new ArrayList<State>();
    HashMap<String, ArrayList<Transition>> partsMap = new HashMap<String, ArrayList<Transition>>();
    HashMap<String, ArrayList<Transition>> dPartsMap = new HashMap<String, ArrayList<Transition>>();
    public Grepy(){
        this.NSTrans = new ArrayList<Transition>();
        this.DSTrans = new ArrayList<Transition>();
        this.alphabet = new ArrayList<Character>();
        // // System.out.println("Grepy Initialized");
    }
    






    public ArrayList<String> pIn( String str) {

        char[] chAr = str.toCharArray();
        ArrayList<String> res = new ArrayList<String>();
        ArrayList<Integer> lParens = new ArrayList<Integer>();
        ArrayList<String> groups = new ArrayList<String>();
        ArrayList<Integer> choices = new ArrayList<Integer>();
        ArrayList<Integer> loops = new ArrayList<Integer>();



        // System.out.println("|"+str);
        if (str.indexOf(')') != -1) {
            for (int i = 0; i < chAr.length; i++) {
                if (chAr[i] == '(') {
                   // // System.out.println("found a (");
                    lParens.add(i);
                } else if (chAr[i] == ')') {
                    // // System.out.println("found a )");
                    int lastInd = lParens.get(lParens.size() - 1);
                   // // System.out.println("Left Parens " + lParens);
                    String group = Integer.toString(lastInd) + '-' + Integer.toString(i + 1);
                    groups.add(group);
                    // // System.out.println("Pop");
                    lParens.remove(lParens.size() - 1);
                } else if (chAr[i] == '+') {
                   // // System.out.println("found a +");
                    choices.add(i);
                } else if (chAr[i] == '*') {
                    // // System.out.println("found a *");
                    if (chAr[(i - 1)] == ')') {
                        for (int x = 0;x<groups.size();x++) {

                            String g = groups.get(x);
                            int pos = g.indexOf('-');

                            String sGroup = g.substring(pos + 1);
                            int end = Integer.parseInt(sGroup);
                            if (end == i) {


                                groups.set(x, g.substring(0, g.indexOf('-') + 1) + (i+1));

                            }
                        }
                    }else{
                        // System.out.println("tried here");
                        String s = Integer.toString((i-1) )+'-'+ Integer.toString((i+1));
                        groups.add(s);

                    }
                }else{
                    String s = Integer.toString((i)) + '-' + Integer.toString((i + 1));
                    if(lParens.size() == 0){

                            // System.out.println("tried here");
                            String st = s;
                            groups.add(st);

                    }
                    
                    
                }

            }
        }else{
            boolean open = false;
            char[] chArr = str.toCharArray();
            for(int v = 0;v<chArr.length;v++){
            if(chArr[v] == ('+')){
                open = false;
                String gre;

                if(str.indexOf("+", v+1) != -1) {
                     gre = Integer.toString((v+1))+ '-' +Integer.toString(str.indexOf("+", v));
                     v = str.indexOf("+", v) + 1;
                     // System.out.println("@@");
                    groups.add(gre);
                }else {
                    //// System.out.println("##");
                    //// System.out.println("fads");
                     gre = Integer.toString((v+1)) + '-' +"end";
                    groups.add(gre);
                    break;

                }


                //skip over next as its already processed
            }else if(chArr[v] == ('*')){

                if(open){
                   // // System.out.println("Resetting");
                    String lGroup = groups.get(groups.size()-1);
                    String subst = lGroup.substring(0,lGroup.indexOf("-")+1);
                    int oNum = Integer.parseInt(lGroup.substring(lGroup.indexOf("-")+1));
                    oNum--;
                    String uGroup = subst + Integer.toString(oNum);
                    groups.set(groups.size()-1, uGroup);
                    open = false;
                }



                String sLoop = (v-1)  + "-"+ (v+1);
                groups.add(sLoop);




                }else{
               // // System.out.println("in here");
                if(!open){
                   // // System.out.println("weree");
                    String b = v + "-" + (v+1);
                    // System.out.println(b);
                    groups.add(b);
                    open = true;
                }else if(open){
                    // System.out.println("221122");
                    String replace = groups.get(groups.size()-1);
                    replace = replace.substring(0,(replace.indexOf("-")+1));
                    replace = replace + Integer.toString(v+1);
                    groups.set(groups.size()-1, replace);

                }


            }

            }







            // // System.out.println("Gotteth");
           // String s = 1 + "-"+(str.length()-1);

        }

        for(String group : groups){
           // System.out.println(group);
            int lBound = Integer.parseInt(group.substring(0,group.indexOf('-')));

            String rBound = group.substring(group.indexOf('-')+1);
            String g;
            if(rBound.equals("end")){
                g = str.substring(lBound);
            }else{
                g = str.substring(lBound,Integer.parseInt((rBound)));
                // System.out.println(g);
            }



            res.add(g);
        }
        return res;


    }

    public boolean mProc(String str){
       boolean valid = false;
        if(str.charAt(0) != '^' || str.charAt(str.length()-1) != '$'){
            return false;
        }else {
            String newStr = str.substring(1, str.length() - 1);


            ArrayList<String> parts = pIn(newStr);



            this.alphabet = pAlpha(newStr);

            for (String part : parts) {
                //// System.out.println("Hello");

                if (part.indexOf('(') != part.lastIndexOf('(')) {
                    continue;
                }
                    nSegments.add(new ArrayList<Transition>());
                    // System.out.println("sending this to be parsed" + part);
                    nPartParser(nSegments.get(nSegments.size() - 1), nStates, part);
                    partsMap.put(part, nSegments.get(nSegments.size() - 1));
                    //// System.out.println(part);

                    //// System.out.println("INSIDE");


            }
            joinNfa(partsMap, newStr, nStates, parts);
            System.out.println("Listing Transitions");
            for(int i= 0;i<nStates.size();i++){
                nStates.get(i).setId(i);


            }
            for(String part : parts){
                ArrayList<Transition> tempParts = partsMap.get(part);

                for(Transition t: tempParts){
                    t.explain();
                }
            }
            State err = new State(dStateCounter++, State.Type.ERROR);

            for(String pt : parts){
                if (pt.indexOf('(') != pt.lastIndexOf('(')) {
                    continue;
                }
                dSegments.add(new ArrayList<Transition>());
                dPartParser(dSegments.get(dSegments.size() -1), dStates, pt, err);
                dPartsMap.put(pt, dSegments.get(dSegments.size() -1));

            }
            joinDfa(dPartsMap, newStr, dStates, parts, err);

            dStates.add(err);
            for(int i= 0;i<dStates.size();i++){
                dStates.get(i).setId(i);
            }


            // System.out.println(parts);


            System.out.println("Listing DFA Transitions");
            for(String part : parts){
                ArrayList<Transition> tempParts = dPartsMap.get(part);
                System.out.println("NEW PART");
                for(Transition t: tempParts){
                    t.explain();
                }
            }

            return valid;
        }
    }

    private void joinDfa(HashMap<String, ArrayList<Transition>> dPartsMap, String str, ArrayList<State> dStates, ArrayList<String> parts, State err) {
        System.out.println("eeeeeeeeeeee");
        ArrayList<String> tParts = new ArrayList<String>();
        for (String part : parts) {
            if (part.indexOf('(') == part.lastIndexOf('(')) {
                tParts.add(part);
            }
        }

        // System.out.println();
        String enumStr = str;
        for(String s : tParts){
            // System.out.print(s + ", ");
            // System.out.println();
        }
        for (int i = 0; i < tParts.size(); i++) {
            // // System.out.println(tParts.get(i));
            String replacement;
            replacement = "(" + i + ")";
            int ind = enumStr.indexOf(tParts.get(i));
            // System.out.println(tParts.get(i) + " starts at "+ ind);
            enumStr = enumStr.substring(0, ind) + replacement + enumStr.substring((ind + tParts.get(i).length()));
            // System.out.println(enumStr);
        }
        // hypothetically by this point we have turned something like ((a+b)+(c+d)) to ((0)+(1))
        System.out.println(enumStr);

        ArrayList<Transition> lTrans = new ArrayList<Transition>();
        if(enumStr.contains("+")) {
            System.out.println(1111);
            if (enumStr.indexOf("+") != enumStr.lastIndexOf("+")) {
                System.out.println(2222);
                int pt = enumStr.indexOf("+");
                State lSt = new State();

                while (pt != -1) {
                    int l1 = 0;
                    int l2 = 0;
                    int r1 = 0;
                    int r2 = 0;
                    if (pt == enumStr.indexOf("+")) {
                        System.out.println(4444);
                        System.out.println("in asdfaewee333");
                        for (int g = 0; g < pt; g++) {
                            if (enumStr.charAt(g) == '(') {
                                l1 = g;
                            } else if (enumStr.charAt(g) == ')') {
                                r1 = g;
                            }
                        }
                        for (int h = enumStr.length() - 1; h > pt; h--) {
                            if (enumStr.charAt(h) == '(') {
                                l2 = h;
                            } else if (enumStr.charAt(h) == ')') {
                                r2 = h;
                            }
                        }


                        System.out.println("NEWQ" + pt);
                        int left = Integer.parseInt(enumStr.substring(l1 + 1, r1));
                        int right = Integer.parseInt(enumStr.substring(l2 + 1, r2));

                         lTrans = dPartsMap.get(tParts.get(left));
                        ArrayList<Transition> rTrans = dPartsMap.get(tParts.get(right));
//                        lTrans.get(lTrans.size() - 1).gettState().setType('A');
                        for(int x = (lTrans.size()-1); x>= 0;x--){
                            if(lTrans.get(x).gettState().getType() != State.Type.ERROR){
                                lTrans.get(x).gettState().setType('A');
                                break;
                            }
                        }
                         lSt = lTrans.get(0).getfState();

                        if(lTrans.get(0).getC() == rTrans.get(0).getC() ){
                            boolean same = true;
                            int lC = 0;

                            while(same){
                                lC++;

                                if(lTrans.get(lC).getC()!= rTrans.get(0).getC()){
                                    break;
                                }
                            }
                            rTrans.get(0).setfState(lTrans.get(lC).getfState());
                        }else{
                            rTrans.get(0).setfState(lSt);
                        }
                        State cTState = rTrans.get(0).fState;
                        for(Transition t: rTrans){
                            if(t.getfState() == cTState){
                                System.out.println("This worked ede");
                                t.setfState(lTrans.get(0).getfState());
                            }
                            else if(t.gettState() == cTState){
                                t.settState(lTrans.get(0).getfState());
                            }
                        }
                        dStates.remove(cTState);






                        if (pt == enumStr.lastIndexOf("+")) {
                            break;
                        } else {
                            pt = enumStr.indexOf("+", pt + 1);
                        }
                    }else{
                        System.out.println(5555);
                        System.out.println("in 2-34040948049844");
                        for (int h = enumStr.length() - 1; h > pt; h--) {
                            if (enumStr.charAt(h) == '(') {
                                l2 = h;
                            } else if (enumStr.charAt(h) == ')') {
                                r2 = h;
                            }
                        }


                        int right = Integer.parseInt(enumStr.substring(l2+1,r2));


                        ArrayList<Transition> rTrans = dPartsMap.get(tParts.get(right));
                        lSt = lTrans.get(0).getfState();
                        if(lTrans.get(0).getC() == rTrans.get(0).getC()){
                            boolean same = true;
                            int lC = 0;

                            while(same){
                                lC++;

                                if(lTrans.get(lC).getC()!= rTrans.get(0).getC()){
                                    break;
                                }
                            }
                            rTrans.get(0).setfState(lTrans.get(lC).getfState());
                            for(int v = 0;v<lTrans.size();v++) {
                                if ((lTrans.get(v).getC() == rTrans.get(0).getC()) && (lTrans.get(v).getfState().getType() == State.Type.ERROR) && lTrans.get(v).getfState() == lTrans.get(lC).getfState()) { // the new intermeddiate state has an error state which conflicts with the
                                    lTrans.remove(v);                                                                                                                                             // new transition, take away that transition as it doesnt make sense anymore
                                }
                            }
                        }else{
                            rTrans.get(0).setfState(lSt);
                            for(int v = 0;v<lTrans.size();v++) {
                                if ((lTrans.get(v).getC() == rTrans.get(0).getC()) && (lTrans.get(v).getfState().getType() == State.Type.ERROR) && lTrans.get(v).getfState() == lTrans.get(0).getfState()) {
                                        lTrans.remove(v);
                                }
                            }
                        }
                        State cTState = rTrans.get(0).fState;
                        for(Transition t: rTrans){
                            if(t.getfState() == cTState){
                                System.out.println("This worked ede");
                                t.setfState(lTrans.get(0).getfState());
                            }
                            else if(t.gettState() == cTState){
                                t.settState(lTrans.get(0).getfState());
                            }
                        }
                        dStates.remove(cTState);



                        if(pt == enumStr.lastIndexOf("+")){
                            break;
                        }else{
                            pt = enumStr.indexOf("+", pt+1);
                        }
                    }

                }
            }else{
                System.out.println(3333);
                int l1 = 0;
                int l2 = 0;
                int r1 = 0;
                int r2 = 0;
                for(int n = 0;n<enumStr.indexOf("+");n++){
                    if(enumStr.charAt(n) == '('){
                        l1 = n;
                    }else if(enumStr.charAt(n) == ')'){
                        r1 = n;
                    }
                }
                for(int m = enumStr.length()-1;m>enumStr.indexOf("+");m--){
                    if(enumStr.charAt(m) == '('){
                        l2= m;
                    }else if(enumStr.charAt(m) == ')'){
                        r2 = m;
                    }
                }

                int left = Integer.parseInt(enumStr.substring(l1+1, r1));
                int right = Integer.parseInt(enumStr.substring(l2+1,r2));

                lTrans = dPartsMap.get(tParts.get(left));
                ArrayList<Transition> rTrans = dPartsMap.get(tParts.get(right));
                //lTrans.get(lTrans.size()-1).gettState().setType('A');
                for(int y = (rTrans.size()-1); y>= 0;y--){
                    if(rTrans.get(y).gettState().getType() != State.Type.ERROR){
                        rTrans.get(y).gettState().setType('A');
                        break;
                    }
                }
                State lSt2 = lTrans.get(0).getfState();
                State cTState = rTrans.get(0).fState;
                for(Transition t: rTrans){
                    if(t.getfState() == cTState){
                        System.out.println("This worked ede");
                        t.setfState(lTrans.get(0).getfState());
                    }
                    else if(t.gettState() == cTState){
                        t.settState(lTrans.get(0).getfState());
                    }
                }
                dStates.remove(cTState);

                if(lTrans.get(0).getC() == rTrans.get(0).getC()){
                    System.out.println(lTrans.get(0).getC() + " matches with "+ rTrans.get(0).getC() );
                    System.out.println("@@@@@");
                    boolean same = true;
                    int lC = 0;

                    while(same){
                        lC++;

                        if(lTrans.get(lC).getC()!= rTrans.get(0).getC()){
                            break;
                        }
                    }
                    rTrans.get(0).setfState(lTrans.get(lC).getfState());
                    System.out.println("@@@@@"+ lC);
                    for(int v = 0;v<lTrans.size();v++) {
                        if ((lTrans.get(v).getC() == rTrans.get(0).getC()) && (lTrans.get(v).gettState().getType() == State.Type.ERROR) && lTrans.get(v).getfState() == lTrans.get(lC).getfState()) { // the new intermeddiate state has an error state which conflicts with the
                            lTrans.remove(v);
                            System.out.println("FOUND CONFILCT");// new transition, take away that transition as it doesnt make sense anymore

                        }
                    }
                }else{
                    System.out.println("%%%%");
                    //rTrans.get(0).setfState(lSt2);
                   for(int v = 0;v<lTrans.size();v++) {
                        if ((lTrans.get(v).getC() == rTrans.get(0).getC()) && (lTrans.get(v).gettState().getType() == State.Type.ERROR) && lTrans.get(v).getfState() == rTrans.get(0).getfState()) {

                            lTrans.remove(v);
                            System.out.println("FOUND CONFILCT");

                        }
                    }
                    for(int y = 0;y<rTrans.size();y++){
                        if(rTrans.get(y).getfState() == lSt2 && rTrans.get(y).gettState().getType() == State.Type.ERROR){
                           // System.out.println("$$$$$$$$$$$$$$$$");
                            rTrans.remove(y);
                        }
                    }
                    System.out.println("LEFT SIDE");
                    for(Transition d : lTrans){
                        d.explain();
                    }
                    ArrayList<Character> alphCpy = new ArrayList<Character>(alphabet);
                    for(int u = 0; u<lTrans.size();u++){
                        if(alphCpy.contains(lTrans.get(u).getC()) && lTrans.get(u).getfState() == lSt2) {
                            char f = lTrans.get(u).getC();
                            lTrans.get(u).explain();
                            System.out.println("REMOVED");
                            alphCpy.remove(alphCpy.indexOf(f));
                        }
                    }
                    System.out.println("#(#(@(@((@(@");

                    for(int u = 0; u<rTrans.size();u++){
                        if(alphCpy.contains(rTrans.get(u).getC()) && rTrans.get(u).getfState() == lSt2) {
                            char f = rTrans.get(u).getC();
                            rTrans.get(u).explain();
                            System.out.println("REMOVED");
                            alphCpy.remove(alphCpy.indexOf(f));
                        }
                    }


                    State lastL = new State();
                    for(int w = (lTrans.size()-1); w>=0;w--){
                        if(lTrans.get(w).gettState().getType() != State.Type.ERROR){
                            lastL = lTrans.get(w).gettState();
                            break;
                        }
                    }
                    for(int w = 0; w<lTrans.size();w++){
                        if(lTrans.get(w).gettState().getType() == State.Type.ERROR && lTrans.get(w).getfState() == lastL){
                            lTrans.remove(w);
                        }
                    }
                    for(Character d : alphCpy){
                        lTrans.add(new Transition(lSt2, err, d));
                    }




                    for(Transition t : rTrans){
                        t.explain();
                    }
                }

            }

        }else{
            int holder = 0;

            System.out.println(214324);
            if(enumStr.indexOf("(") != enumStr.lastIndexOf("(")){
                System.out.println("AEEE");
                ArrayList<Transition> cSet = new ArrayList<Transition>();
                State lastS = new State();
                for(int x = 0;x<tParts.size();x++){
                    ArrayList<Transition> temp = dPartsMap.get(tParts.get(x));
                    if(x == 0){

                        for(int y = (temp.size()-1); y>= 0;y--){
                            if(temp.get(y).gettState().getType() != State.Type.ERROR){
                               lastS =  temp.get(y).gettState();
                               System.out.println("Fouaeeeeeeeeeend");

                               break;
                            }
                            temp.get(y).explain();
                        }



                    }else{
                        System.out.println("Diagnostics:");
                        for(Transition t : temp){
                            t.explain();
                        }
                        System.out.println("Getting in here");
                        cSet = dPartsMap.get(tParts.get(x));
                        State cTState = cSet.get(0).fState;

                        for(Transition t: cSet){
                            if(t.getfState() == cTState){
                                System.out.println("This worked ede");
                                t.setfState(lastS);
                            }
                            else if(t.gettState() == cTState){
                                t.settState(lastS);
                            }
                        }
                        //cSet.get(0).setfState(lastS);
                        dStates.remove(cTState);
                        ArrayList<Transition> lastSet = dPartsMap.get(tParts.get(x-1));
//                        for(int v = 0;v<lastSet.size();v++) {
//                            if ((lastSet.get(v).getC() == cSet.get(0).getC()) && (lastSet.get(v).getfState().getType() == State.Type.ERROR)) {
//                                lastSet.remove(v);
//                                System.out.println("OOF");
//                            }
//                        }

                        for(int  d = 0;d<cSet.size();d++){
//
                            if(cSet.get(d).getfState() == lastS && cSet.get(d).gettState().getType() == State.Type.ERROR){
                                System.out.println("----------");
                                cSet.get(d).explain();
                                cSet.remove(d);

                                System.out.println("####REMOVED####");
                                if(cSet.size() == 0){
                                    break;
                                }

                            }
//
                        }
                        for(int c = 0;c<lastSet.size();c++){

                            if(lastSet.get(c).getfState() == lastS && lastSet.get(c).gettState().getType() == State.Type.ERROR){
                                System.out.println("FOUND AN ERROR");
                                if(lastSet.get(c).getC() == cSet.get(0).getC()){
                                    System.out.println("__________________");
                                    lastSet.get(c).explain();
                                    System.out.println("Remmed from new set");
                                    lastSet.remove(c);
                                    if(lastSet.size() == 0){
                                       break;
                                    }
                                }
                            }
                        }



                        for(int y = (temp.size()-1); y>= 0;y--){
                            if(temp.get(y).gettState().getType() != State.Type.ERROR){
                                lastS =  temp.get(y).gettState();
                                System.out.println("Found");
                                break;
                            }
                        }
//                        partsMap.get(tParts.get(x-1)).add(new Transition(lastS,cTState,'~'));
//                        State cSetLast = cSet.get(cSet.size()-1).tState;
//                        lastS = cSetLast;
                    }
                }
                lastS.setType('A');
                ArrayList<Character> alphCpy = new ArrayList<Character>(alphabet);
                for(int u = 0; u<cSet.size();u++){
                    if(alphCpy.contains(cSet.get(u).getC()) && cSet.get(u).getfState() == lastS) {
                        char f = cSet.get(u).getC();
                        alphCpy.remove(alphCpy.indexOf(f));
                    }
                }

                for(Character d : alphCpy){
                    cSet.add(new Transition(lastS, err, d));
                }
            }
        }

    }

    private void dPartParser(ArrayList<Transition> section, ArrayList<State> dStates,  String str, State err) {
        ArrayList<Character> subAlpha = pAlpha(str);
        // System.out.println("parsing"+ str);
        State s = new State(dStateCounter++, State.Type.INTER);
        s.setStart(true);
        dStates.add(s);


        if (str.indexOf('*') >= 0) {
            if (str.indexOf('+') >= 0) {
                // // System.out.println(1111);
                d_cLoop(section, dStates, str, subAlpha, s, err);  //choice loop
            } else {
                // // System.out.println(2222);
                d_pLoop(section, dStates, str, subAlpha, s, err); //pattern
            }
        } else if (str.indexOf('+') >= 0) {
            // // System.out.println(4444);
            d_choice(section, dStates, str, subAlpha, s, err);       //simple choice
        }  else {
            // // System.out.println("getting here");
            d_exact(section, dStates, str, subAlpha, s, err);         //single character / non looping pattern
        }

    }

    private void d_exact(ArrayList<Transition> section, ArrayList<State> dStates, String str, ArrayList<Character> subAlpha, State s, State err) {
        State prev = s;
        String loop;
        if(str.contains("(")) {
            loop  =str.substring(str.indexOf('(') + 1, str.indexOf(')'));
        }else{
            loop = str;
        }

        ArrayList<Character> erC = this.alphabet;
        if(loop.length() > 1){
            for(int i = 0;i<loop.length();i++) {
                System.out.println("DOING " + loop.charAt(i));
                System.out.println("2344111");


                State st = new State(dStateCounter++, State.Type.INTER);
                dStates.add(st);
                section.add(new Transition(prev, st, loop.charAt(i)));
                if(i == 0) {
                    for (Character c : erC) {
                        if (c == loop.charAt(i)) {
                            continue;
                        }
                        section.add(new Transition(prev, err, c));
                    }
                    System.out.println("Beep boop");
                    prev = st;
                    continue;
                }else{
                    for(Character c : erC){
                        if(c == loop.charAt(i)){
                            continue;
                        }
                        section.add(new Transition(prev,err,c));
                    }
                    prev = st;

                }


                System.out.println("Beep3333 boop");

            }
            for(Character c : erC){

                section.add(new Transition(prev,err,c));
            }

        }else{
            State nState = new State(dStateCounter++, State.Type.INTER);
            dStates.add(nState);
            ArrayList<Character> erC2 = this.alphabet;

            // System.out.println("this is the break" + loop);
            section.add(new Transition(prev, nState, loop.charAt(0)));
            for(char d : erC2){
                if(d == loop.charAt(0)){
                    continue;
                }
                section.add(new Transition(nState, err, d));

            }

        }
    }

    private void d_choice(ArrayList<Transition> section, ArrayList<State> dStates, String str, ArrayList<Character> subAlpha, State s, State err) {
        State next = new State(dStateCounter++, State.Type.ACCEPT);
        dStates.add(next);
        ArrayList<Character> erC = this.alphabet;
        for(char c : subAlpha){

            erC.remove(c);
            section.add(new Transition(s,next, c));

        }
        for(char x : erC){
            section.add(new Transition(s,err,x ));
        }
    }

    private void d_pLoop(ArrayList<Transition> section, ArrayList<State> dStates, String str, ArrayList<Character> subAlpha, State s, State err) {
        State prev = s;
        String loop;
        if(str.contains("(")) {
            loop  =str.substring(str.indexOf('(') + 1, str.indexOf(')'));
        }else{
            loop = Character.toString(str.charAt(1));
        }


        if(loop.length() > 1){
            for(int i = 0;i<loop.length();i++) {
                ArrayList<Character> erC = this.alphabet;

                if(i == loop.length()-1){
                    section.add(new Transition(prev, s, loop.charAt(i)));
                    System.out.println("(((((((((((");
                    System.out.println(prev.explain());
                    for(Character c : erC){
                        if(c == loop.charAt(i)){
                            continue;
                        }
                        section.add(new Transition(prev,err,c));
                    }
                }else{
                    State st = new State(dStateCounter++, State.Type.INTER);
                    dStates.add(st);
                    section.add(new Transition(prev, st, loop.charAt(i)));

                    if(i == 0) {
                        System.out.println(11112222);
                        for (Character c : erC) {
                            if (c == loop.charAt(i)) {
                                continue;
                            }
                            section.add(new Transition(prev, err, c));
                        }
                        prev = st;

                        continue;
                    }else{
                       // prev = st;
                        System.out.println("49322112");

                        for(Character c : erC){
                            if(c == loop.charAt(i)){
                                continue;
                            }
                            section.add(new Transition(prev,err,c));
                        }
                        prev = st;
                    }


                }

            }

        }else{
            d_cLoop(section, dStates, str, subAlpha, s, err);
        }
//            for(char a : this.alphabet){
//                if(a != loop.charAt(0)){
//                    section.add(new Transition(s,err,a));
//                }
//            }
    }

    private void d_cLoop(ArrayList<Transition> section, ArrayList<State> dStates, String str, ArrayList<Character> subAlpha, State s, State err) {
        for(char c : subAlpha){
            section.add(new Transition(s,s,c));
        }
        for(char x: this.alphabet){
            if(!subAlpha.contains('x')){
                section.add(new Transition(s,err,x));
            }
        }
    }


    private ArrayList<Character> pAlpha(String str) {
        char[] full = str.toCharArray();
        ArrayList<Character> alpha = new ArrayList<Character>();
        for (char c : full) {
            if (!alpha.contains(c)) {
                alpha.add(c);
            }
            ArrayList<Character> special = new ArrayList<Character>();
            special.add('(');
            special.add(')');
            special.add('+');
            special.add('*');


            alpha.removeAll(special);

        }
        return alpha;
    }

    public void nPartParser(ArrayList<Transition> section, ArrayList<State> nStates, String str) {
        ArrayList<Character> subAlpha = pAlpha(str);
        // System.out.println("parsing"+ str);
        State s = new State(stateCounter++, State.Type.INTER);
        s.setStart(true);
        nStates.add(s);


            if (str.indexOf('*') >= 0) {
                if (str.indexOf('+') >= 0) {
                    // // System.out.println(1111);
                    n_cLoop(section, nStates, str, subAlpha, s);  //choice loop
                } else {
                    // // System.out.println(2222);
                    n_pLoop(section, nStates, str, subAlpha, s); //pattern
                }
            } else if (str.indexOf('+') >= 0) {
                // // System.out.println(4444);
                n_choice(section, nStates, str, subAlpha, s);       //simple choice
            }  else {
                // // System.out.println("getting here");
                n_exact(section, nStates, str, subAlpha, s);         //single character / non looping pattern
            }


    }

    private void n_exact(ArrayList<Transition> section, ArrayList<State> nStates, String str, ArrayList<Character> subAlpha, State s) {
        // System.out.println(4444);
        State prev = s;
        String loop;
        if(str.contains("(")) {
            loop  =str.substring(str.indexOf('(') + 1, str.indexOf(')'));
        }else{
            loop = str;
        }


        if(loop.length() > 1){
            for(int i = 0;i<loop.length();i++) {

                    State st = new State(stateCounter++, State.Type.INTER);
                    nStates.add(st);
                    section.add(new Transition(prev, st, loop.charAt(i)));
                    prev = st;


            }

        }else{
            State nState = new State(stateCounter++, State.Type.INTER);
            nStates.add(nState);
            // System.out.println("this is the break" + loop);
            section.add(new Transition(prev, nState, loop.charAt(0)));
        }
    }




    private void n_cLoop(ArrayList<Transition> section, ArrayList<State> nStates, String str, ArrayList<Character> subAlpha, State s) {
        // System.out.println(1111);
        for(char c : subAlpha){
           section.add(new Transition(s,s,c));
        }
    }

    private void n_pLoop(ArrayList<Transition> section, ArrayList<State> nStates, String str, ArrayList<Character> subAlpha, State s) {
        // System.out.println(2222);

        State prev = s;
        String loop;
               if(str.contains("(")) {
                 loop  =str.substring(str.indexOf('(') + 1, str.indexOf(')'));
               }else{
                   loop = Character.toString(str.charAt(1));
               }

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

        }else{
            n_cLoop(section, nStates, str, subAlpha, s);
        }
    }
    private void n_choice(ArrayList<Transition> section, ArrayList<State> nStates, String str, ArrayList<Character> subAlpha, State s) {
        // System.out.println(3333);
       State next = new State(stateCounter++, State.Type.ACCEPT);
       nStates.add(next);
       for(char c : subAlpha){
           section.add(new Transition(s,next, c));
       }
    }




    private void joinNfa(HashMap<String, ArrayList<Transition>> partsMap, String str, ArrayList<State> nStates, ArrayList<String> parts) {
        // HashMap<String, ArrayList<String>> mappedParts = new HashMap<String,ArrayList<String>>();

        ArrayList<String> tParts = new ArrayList<String>();
        for (String part : parts) {
            if (part.indexOf('(') == part.lastIndexOf('(')) {
                tParts.add(part);
            }
        }

        // System.out.println();
        String enumStr = str;
        for(String s : tParts){
             // System.out.print(s + ", ");
             // System.out.println();
        }
        for (int i = 0; i < tParts.size(); i++) {
            // // System.out.println(tParts.get(i));
            String replacement;
            replacement = "(" + i + ")";
            int ind = enumStr.indexOf(tParts.get(i));
            // System.out.println(tParts.get(i) + " starts at "+ ind);
            enumStr = enumStr.substring(0, ind) + replacement + enumStr.substring((ind + tParts.get(i).length()));
            // System.out.println(enumStr);
        }
        // hypothetically by this point we have turned something like ((a+b)+(c+d)) to ((0)+(1))






       //   System.out.println(enumStr);
        ArrayList<Integer> accum = new ArrayList<Integer>();
        boolean waiting = false;
        if(enumStr.contains("+")){
            State nSt = new State(-1, State.Type.INTER);
            State endS = new State(stateCounter++, State.Type.ACCEPT);
            nSt.setStart(true);
            nStates.add(endS);
            nStates.add(0,nSt);

          if(enumStr.indexOf("+")!= enumStr.lastIndexOf("+")){


              int pt = enumStr.indexOf("+");
              State lSt = new State();
              while(pt != -1) {
                  int l1 = 0;
                  int l2 = 0;
                  int r1 = 0;
                  int r2 = 0;
                  if (pt == enumStr.indexOf("+")) {
                      System.out.println("in asdfaewee333");
                      for (int g = 0; g < pt; g++) {
                          if (enumStr.charAt(g) == '(') {
                              l1 = g;
                          } else if (enumStr.charAt(g) == ')') {
                              r1 = g;
                          }
                      }
                      for (int h = enumStr.length() - 1; h > pt; h--) {
                          if (enumStr.charAt(h) == '(') {
                              l2 = h;
                          } else if (enumStr.charAt(h) == ')') {
                              r2 = h;
                          }
                      }


                      System.out.println("NEWQ"+ pt);
                      int left = Integer.parseInt(enumStr.substring(l1+1, r1));
                      int right = Integer.parseInt(enumStr.substring(l2+1,r2));

                      ArrayList<Transition> lTrans = partsMap.get(tParts.get(left));
                      ArrayList<Transition> rTrans = partsMap.get(tParts.get(right));
                     State clEnd = lTrans.get(lTrans.size()-1).gettState();
                      State crEnd = rTrans.get(rTrans.size()-1).gettState();
                      lTrans.add(new Transition(clEnd, endS, '~'));
                      rTrans.add(new Transition(crEnd, endS, '~'));
                       lSt = lTrans.get(0).fState;
                      lSt.setStart(false);

                      State r1State = rTrans.get(0).fState;
                        r1State.setStart(false);
                      lTrans.add(0, new Transition(nSt,lSt, '~'));
                      rTrans.add(0, new Transition(nSt,r1State, '~'));
                      if(pt == enumStr.lastIndexOf("+")){
                          break;
                      }else{
                          pt = enumStr.indexOf("+", pt+1);
                      }
                  }else{
                      System.out.println("in 2-34040948049844");
                      for (int h = enumStr.length() - 1; h > pt; h--) {
                          if (enumStr.charAt(h) == '(') {
                              l2 = h;
                          } else if (enumStr.charAt(h) == ')') {
                              r2 = h;
                          }
                      }


                      int right = Integer.parseInt(enumStr.substring(l2+1,r2));


                      ArrayList<Transition> rTrans = partsMap.get(tParts.get(right));

                      State ccrEnd = rTrans.get(rTrans.size()-1).gettState();
                      rTrans.add(new Transition(ccrEnd, endS, '~'));
                      State r1State = rTrans.get(0).fState;
                        r1State.setStart(false);

                      rTrans.add(0, new Transition(nSt,r1State, '~'));
                      pt = enumStr.indexOf("+", pt);
                      if(pt == enumStr.lastIndexOf("+")){
                          break;
                      }else{
                          pt = enumStr.indexOf("+", pt+1);
                      }
                  }
              }
          }else{
              int l1 = 0;
              int l2 = 0;
              int r1 = 0;
              int r2 = 0;
              for(int n = 0;n<enumStr.indexOf("+");n++){
                    if(enumStr.charAt(n) == '('){
                        l1 = n;
                    }else if(enumStr.charAt(n) == ')'){
                        r1 = n;
                    }
              }
              for(int m = enumStr.length()-1;m>enumStr.indexOf("+");m--){
                  if(enumStr.charAt(m) == '('){
                      l2= m;
                  }else if(enumStr.charAt(m) == ')'){
                      r2 = m;
                  }
              }

              int left = Integer.parseInt(enumStr.substring(l1+1, r1));
              int right = Integer.parseInt(enumStr.substring(l2+1,r2));
              //State nSt = new State(-1, State.Type.INTER);
//              nSt.setStart(true);
//              nStates.add(0,nSt);
              ArrayList<Transition> lTrans = partsMap.get(tParts.get(left));
              ArrayList<Transition> rTrans = partsMap.get(tParts.get(right));
              State curLEnd = lTrans.get(lTrans.size()-1).gettState();
              State curREnd = rTrans.get(rTrans.size()-1).gettState();
              lTrans.add(new Transition(curLEnd, endS, '~'));
              rTrans.add(new Transition(curREnd, endS,'~'));

              State l1State = lTrans.get(0).fState;
              l1State.setStart(false);
              State r1State = rTrans.get(0).fState;
              r1State.setStart(false);
              lTrans.add(0, new Transition(nSt,l1State, '~'));
              rTrans.add(0,new Transition(nSt,r1State, '~'));

          }

        }else{

            if(enumStr.indexOf("(") != enumStr.lastIndexOf("(")){
                System.out.println("AEEE");

                State lastS = new State();
                for(int x = 0;x<tParts.size();x++){
                    if(x == 0){
                       lastS =  partsMap.get(tParts.get(x)).get(partsMap.get(tParts.get(x)).size()-1).tState;

                    }else{
                        ArrayList<Transition> cSet = partsMap.get(tParts.get(x));
                        State cTState = cSet.get(0).fState;
                        cTState.setStart(false);
                        partsMap.get(tParts.get(x-1)).add(new Transition(lastS,cTState,'~'));
                        State cSetLast = cSet.get(cSet.size()-1).tState;
                        lastS = cSetLast;
                    }
                }
                lastS.setType('A');
            }
        }

    }




}
