package guru.qa;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("simple")
public class NegativeTests {

    @Test
    void someTest(){
        assertTrue(false);
    }

    @Test
    void someTest1(){
        assertTrue(false);
    }

    @Test
    void someTest2(){
        assertTrue(false);
    }
}
