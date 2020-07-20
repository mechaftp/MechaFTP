package edu.pdx.cs;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginCommandTest {
    @Test
    public void verifyExecuteReturnValue(){
        LoginCommand Login = new LoginCommand();
        assertThat(Login.execute(), equalTo(0));
    }
}
