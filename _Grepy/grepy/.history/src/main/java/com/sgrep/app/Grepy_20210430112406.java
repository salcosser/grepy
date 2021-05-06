import java.util.*;
package com.sgrep.Gre;
public class Grepy{
    public ArrayList<Character> parseAlpha(String rawReg){
        ArrayList<Character> alphabet = new ArrayList<Character>();

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
