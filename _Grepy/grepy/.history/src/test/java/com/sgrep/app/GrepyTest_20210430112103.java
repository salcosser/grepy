package com.sgrep.app;
import main.java.com.sgrep.aGrepy;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GrepyTest{

    @Test
    public void shouldValidate(){
        assertEquals(8,Grepy.t1("hi"));
    }
}