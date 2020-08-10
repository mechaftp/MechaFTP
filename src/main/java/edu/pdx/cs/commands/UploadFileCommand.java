package edu.pdx.cs.commands;

import edu.pdx.cs.Client;
import edu.pdx.cs.ClientState;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UploadFileCommand extends BaseCommand
{
    public UploadFileCommand(Client client, List<String> subarguments)
    {
        super(client, subarguments);
        expectNSubarguments(1);
    }

    @Override
    public boolean execute(ClientState state)
    {
        File file = new File(arguments.get(0));
        try
        {
            boolean result = client.uploadFile(file);
            if (result)
            {
                state.output("Uploaded file " + file.getName());
            }
            else
                state.output("Couldn't upload file.");
            return result;
        }
        catch (IOException e)
        {
            state.output("Couldn't upload file: " + e.getMessage());
            return false;
        }
    }
}
