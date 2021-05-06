package main.java.com.sgrep.proc;
import main.java.com.sgrep.Grepy;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

public class GrepyTest{

    @Test
    public void shouldValidate(){
        Grepy gr = new Grepy();
        assertEquals(8,gr.t1("hi"));
    }
}