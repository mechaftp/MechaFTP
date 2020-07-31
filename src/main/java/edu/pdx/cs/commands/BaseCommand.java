package edu.pdx.cs.commands;

import edu.pdx.cs.Client;

import java.util.List;

public abstract class BaseCommand implements Command
{
    protected Client client;
    protected List<String> arguments;

    protected BaseCommand(Client client, List<String> arguments)
    {
        this.arguments = arguments;
    }

    public abstract boolean execute();

    protected void expectNSubarguments(int N)
    {
        if (this.arguments.size() != N)
            throw new IllegalArgumentException("Wrong number of arguments.");
    }
}
