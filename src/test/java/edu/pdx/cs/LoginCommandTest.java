package edu.pdx.cs;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginCommandTest {
    @Test
    public void verifyExecuteReturnValue(){
        assertThat(Command.class.execute(), equalTo(0));
    }
}
