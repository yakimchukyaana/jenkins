package guru.qa;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("simple")
public class SkippedTests {

    @Test
    @Disabled("Some reason")
    void someTest(){
        assertTrue(true);
    }

    @Test
    @Disabled
    void someTest1(){
        assertTrue(true);
    }

    @Test
    @Disabled
    void someTest2(){
        assertTrue(true);
    }
}
