package edu.pdx.cs;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CommandFactoryTest {
    @Test
    public void CanCreateLoginCommand(){
        assertThat(CommandFactory.createLogin() instanceof LoginCommand, equalTo(true));
    }
}
