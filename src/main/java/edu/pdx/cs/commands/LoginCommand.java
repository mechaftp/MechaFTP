package edu.pdx.cs.commands;

import edu.pdx.cs.Client;
import edu.pdx.cs.ClientState;

import java.io.IOException;
import java.util.List;

public class LoginCommand extends BaseCommand
{

    public LoginCommand(Client client, List<String> arguments)
    {
        super(client, arguments);
        expectNSubarguments(2);
    }

    @Override
    public boolean execute(ClientState state)
    {
        String username = arguments.get(0);
        String password = arguments.get(1);
        if (username == null || password == null)
            throw new IllegalStateException("Attempted execution before assigning input.");
        try
        {
            state.setLoggedIn(client.login(username, password));
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
    }
}
