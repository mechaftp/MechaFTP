package edu.pdx.cs.commands;

import edu.pdx.cs.Client;
import edu.pdx.cs.ClientState;

import java.util.List;

public abstract class BaseCommand implements Command
{
    protected Client client;
    protected List<String> arguments;

    protected BaseCommand(Client client, List<String> arguments)
    {
        this.arguments = arguments;
    }

    public abstract boolean execute(ClientState state);

    protected void expectNSubarguments(int N)
    {
        if (this.arguments.size() != N)
            throw new IllegalArgumentException("Wrong number of arguments.");
    }
}
