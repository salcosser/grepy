package com.sgrep.proc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> testCases = new ArrayList<String>();
        Grepy gr;
        String regex;
        if (args.length == 3) {

           gr  = new Grepy(args[0], args[1]);
            File file = new File(args[2]);
            Scanner sc = new Scanner(file);
             regex = "";
            boolean first = true;
            while (sc.hasNextLine()){
                if(first){
                    regex = sc.nextLine();
                    first = false;
                }else{
                    testCases.add(sc.nextLine());
                }
            }





            ArrayList<String> out = gr.pIn(regex);
            gr.mProc(regex);
        } else {

             gr = new Grepy("", "");
            File file = new File(args[0]);
            Scanner sc = new Scanner(file);
             regex = "";
            boolean first = true;
            while (sc.hasNextLine()){
                if(first){
                    regex = sc.nextLine();
                    first = false;
                }else{
                    testCases.add(sc.nextLine());
                }
            }


            ArrayList<String> out = gr.pIn(regex);
            gr.mProc(regex);
        }
        for(String s : testCases){
            if(gr.passes(s)){
                System.out.println(s + " is accepted by this regex.");
            }else{
                System.out.println(s + " is not accepted by this regex" );
            }
        }


        boolean running = true;
        System.out.println("Welcome to grepy! a regex parser based on finite automata!");
        while(running){
            Scanner sc = new Scanner(System.in);
            System.out.println("Either input a string to be tested against the regex " + regex + ", or quit() to end.");
            String inp = sc.nextLine();
            if(inp.equals("quit()")){
                System.out.println("Goodbye.");
                running = false;
            }else{
                if(gr.passes(inp)){
                    System.out.println(inp + " is accepted by this regex.");
                }else{
                    System.out.println(inp + " is not accepted by this regex" );
                }
            }
        }





        // System.out.println(gr.nStates.size());
    }
}
//        StringBuilder res = new StringBuilder();
////        for(String part : out){
////            res.append(part).append(", ");
////        }
//        String realRes = res.toString();
//        System.out.println(realRes);

