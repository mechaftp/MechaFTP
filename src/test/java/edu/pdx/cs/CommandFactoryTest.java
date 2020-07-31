package edu.pdx.cs;

import edu.pdx.cs.commands.LoginCommand;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;

import static org.hamcrest.MatcherAssert.assertThat;


public class CommandFactoryTest {
    @Test
    public void CanCreateLoginCommand(){
        Client client = new Client();
        List<String> test = List.of("user", "pass");
        assertThat(CommandFactory.createLogin(client, test), instanceOf(LoginCommand.class));
    }
}
