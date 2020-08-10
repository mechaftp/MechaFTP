package edu.pdx.cs.commands;

import edu.pdx.cs.Client;
import edu.pdx.cs.ClientState;

import java.io.IOException;
import java.nio.file.Paths;
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
            boolean result = client.login(username, password);
            state.setLoggedIn(result);
            if (result)
            {
                state.output("Logged into server as " + username + ".");
                state.setRemoteCwd(Paths.get(client.printWorkingDirectory()));
            }
            else
                state.output("Failed to log into server with username " + username + ".");
            
            return result;
        }
        catch (IOException e)
        {
            return false;
        }
    }
}
