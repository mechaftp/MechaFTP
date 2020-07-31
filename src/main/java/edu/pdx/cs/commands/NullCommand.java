package edu.pdx.cs.commands;

import edu.pdx.cs.Client;

import java.util.List;

public class NullCommand extends BaseCommand
{
    public NullCommand(Client client, List<String> subarguments)
    {
        super(client, subarguments);
    }

    @Override
    public boolean execute()
    {
        return true;
    }
}
