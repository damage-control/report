package junit;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SampleJUnitTest {

    @Test
    public void shouldDoSomething() {
        assertThat("value", is("value"));
    }

    @Test
    public void shouldDoSomethingThatFails() {
        assertThat("value", is("unexpected value"));
    }
}
