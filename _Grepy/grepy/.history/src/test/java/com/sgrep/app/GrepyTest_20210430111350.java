package com.sgrep.app;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GrepyTest{

    @Test
    public void shouldValidate(){
        assertE(8,Grepy.t1("hi") );
    }
}