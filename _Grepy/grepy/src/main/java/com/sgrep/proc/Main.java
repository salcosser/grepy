package com.sgrep.proc;

import java.util.ArrayList;

public class Main {
    public static void main(String [] args){
        Grepy gr = new Grepy();
        ArrayList<String> out = gr.pIn(args[0]);
        StringBuilder res = new StringBuilder();
        for(String part : out){
            res.append(part).append(", ");
        }
        String realRes = res.toString();
        System.out.println(realRes);
        gr.mProc(args[0]);
        System.out.println(gr.stateCounter);
    }
}
