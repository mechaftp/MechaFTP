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
    public void init() throws IOException
    {
        client = mock(Client.class);
        when(client.login("Bob", "password")).thenReturn(true);
    }


//    @Test
//    public void verifyExecuteReturnValue(){
//
//        LoginCommand login = new LoginCommand(client, List.of("Bob", "password"));
//        assertThat(login.execute(), equalTo(true));
//    }

    @Test(expected = IllegalArgumentException.class)
    public void assignWrongNumberOfSubcommandsThrows()
    {
        LoginCommand login = new LoginCommand(client, List.of("a", "b", "c"));
    }

//    @Test
//    public void invalidLoginReturnsFalse()
//    {
//        LoginCommand login = new LoginCommand(client, List.of("wrong", "notpassword"));
//        assertThat(login.execute(), equalTo(false));
//    }
}
