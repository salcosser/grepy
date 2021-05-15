package com.sgrep.proc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        if (args.length == 3) {

            Grepy gr = new Grepy(args[0], args[1]);
            File file = new File(args[2]);
            Scanner sc = new Scanner(file);
            String regex = "";
            while (sc.hasNextLine())
                regex = sc.nextLine();


            ArrayList<String> out = gr.pIn(regex);
            gr.mProc(regex);
        } else {

            Grepy gr = new Grepy("", "");
            File file = new File(args[0]);
            Scanner sc = new Scanner(file);
            String regex = "";
            while (sc.hasNextLine())
                regex = sc.nextLine();


            ArrayList<String> out = gr.pIn(regex);
            gr.mProc(regex);
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

