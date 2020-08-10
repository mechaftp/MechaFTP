package edu.pdx.cs.commands;

import edu.pdx.cs.Client;
import edu.pdx.cs.ClientState;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RetrieveFileCommand extends BaseCommand
{
    public RetrieveFileCommand(Client client, List<String> subarguments)
    {
        super(client, subarguments);
        expectNSubarguments(1);
    }

    @Override
    public boolean execute(ClientState state)
    {
        String filename = arguments.get(0);
        try
        {
            boolean result = client.retrieveFile(filename);
            if (result)
            {
                state.output("Downloaded file " + filename);
            }
            else
                state.output("Couldn't download file: " + filename);
            return result;
        }
        catch (IOException e)
        {
            state.output("Couldn't download file: " + e.getMessage());
            return false;
        }
    }
}
