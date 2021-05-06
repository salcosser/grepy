package main.java.com.sgrep.proc;
import com.sgrep.Grepy;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

public class GrepyTest{

    @Test
    public void shouldValidate(){
        assertEquals(8,Grepy.t1("hi"));
    }
}