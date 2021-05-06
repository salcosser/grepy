package com.sgrep.app;
import main.java.com.sgrep.app.Grepy;
import static org.junit.Assert.assertTrue;
C:\SANDBOX\grepy\_Grepy\grepy\src\main\java\com\sgrep\app\Grepy.java
import org.junit.Test;

public class GrepyTest{

    @Test
    public void shouldValidate(){
        assertEquals(8,Grepy.t1("hi"));
    }
}