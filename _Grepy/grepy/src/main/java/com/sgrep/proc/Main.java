package com.sgrep.proc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> testCases = new ArrayList<String>();
        Grepy gr=  null;
        String regex = null;
        if (args.length == 3) { // if input files and the regex file is specified

           gr  = new Grepy(args[0], args[1]); // arg 0 is for the nfa, arg 1 is for the dfa
            File file = new File(args[2]);
            Scanner sc = new Scanner(file);
             regex = "";
            boolean first = true;
            while (sc.hasNextLine()){
                if(first){
                    regex = sc.nextLine(); // load the regex
                    first = false;
                }else{
                    testCases.add(sc.nextLine()); // if there were any test cases in the regex file, try them
                }
            }





            ArrayList<String> out = gr.pIn(regex);
            gr.mProc(regex);
        } else if(args.length == 1) {

             gr = new Grepy("", ""); // pass blank strings if no dfa/ nfa files were specified, telling the program to create ones
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
        }else{
            System.out.println("Please input at least the regex file location");
        }
        for(String s : testCases){ // run the test cases
            if(gr.passes(s)){
                System.out.println(s + " is accepted by this regex.");
            }else{
                System.out.println(s + " is not accepted by this regex" );
            }
        }


        boolean running = true;                                                                                                                     // cli part
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






    }
}

