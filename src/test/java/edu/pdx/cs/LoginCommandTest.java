package edu.pdx.cs;

import edu.pdx.cs.commands.LoginCommand;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginCommandTest {
    public static Client client;

    @Before
    public void init() throws IOException {
        client = mock(Client.class);
        when(client.login("Bob", "password")).thenReturn(true);
    }


    @Test(expected = IllegalArgumentException.class)
    public void assignWrongNumberOfSubcommandsThrows() {
        new LoginCommand(client, List.of("a", "b", "c"));
    }

}
