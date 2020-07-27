package edu.pdx.cs.commands;

import edu.pdx.cs.Client;
import edu.pdx.cs.Command;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class LoginCommand extends Command
{
    private String username;
    private String password;

    public LoginCommand(Client client) { super(client); }

    @Override
    public boolean execute() {
        if (username == null || password == null)
            throw new IllegalStateException("Attempted execution before assigning input.");
        try
        {
            return client.login(username, password);
        }
        catch (IOException e)
        {
            return false;
        }
    }

    @Override
    public void assignInput(List<String> subcommands)
    {
        if (subcommands.size() != 2)
            throw new IllegalArgumentException("Unexpected number of subcommands for LoginCommand.");
        username = subcommands.get(0);
        password = subcommands.get(1);
    }
}
