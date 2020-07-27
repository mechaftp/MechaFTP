package edu.pdx.cs;

import edu.pdx.cs.commands.LoginCommand;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.hamcrest.MatcherAssert.assertThat;


public class CommandFactoryTest {
    @Test
    public void CanCreateLoginCommand(){
        Client client = new Client();
        assertThat(CommandFactory.createLogin(client), instanceOf(LoginCommand.class));
    }
}
