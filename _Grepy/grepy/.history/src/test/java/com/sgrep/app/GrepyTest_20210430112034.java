package com.sgrep.app;
import 
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GrepyTest{

    @Test
    public void shouldValidate(){
        assertEquals(8,Grepy.t1("hi"));
    }
}