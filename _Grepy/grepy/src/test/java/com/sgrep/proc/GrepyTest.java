package com.sgrep.proc;
// import main.java.com.sgrep.proc.Grepy;
// import main.java.com.sgrep.proc.State;
import static org.junit.Assert.assertTrue;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sgrep.proc.Grepy;
import org.junit.Test;

public class GrepyTest{

    @Test
    public void shouldValidate(){
       // Grepy gr = new Grepy();

        ArrayList<String> ans = new ArrayList<String>();
//        ans.add("(a+b)*");
////        ans.add("(c+d)");
////        ans.add("((a+b)*+(c+d))");
        ans.add("(c+d)");
        ans.add("(a+b)");
        ans.add("((c+d)+(a+b))*");
      ans.add("x*");
       ans.add("(((c+d)+(a+b))*+x*)");
//       ans.add("(a)*");
        ArrayList<String> cGroups = new ArrayList<String>();
      //  assertEquals(ans, gr.pIn("(((c+d)+(a+b))*+x*)"));
    }
}