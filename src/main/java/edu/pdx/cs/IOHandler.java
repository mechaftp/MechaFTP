package edu.pdx.cs;

import edu.pdx.cs.commands.Command;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class IOHandler {
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private Validator validator = new Validator();
    public List<String> tokens;
    private Client client;
    public boolean quitting;


    IOHandler(Client client) {
        this.client = client;
    }

    public void readInput()
    {
        try
        {
            String input = bufferedReader.readLine();
            this.tokens = List.of(input.split("\\s+"));
        }
        catch (IOException | NullPointerException e)
        {
            this.tokens = List.of();
        }
    }

    public Command parseInput()
    {
        List<String> subarguments = tokens.subList(1, tokens.size());
        Command command;
        try
        {
            switch (tokens.get(0))
            {
                case "login":
                    command = CommandFactory.createLogin(client, subarguments);
                    break;
                case "logout":
                    command = CommandFactory.createLogout(client, subarguments);
                    break;
                case "listRemoteDirectories":
                    command = CommandFactory.createListRemoteDirectories(client, subarguments);
                    break;
                case "listRemoteFiles":
                    command = CommandFactory.createListRemoteFiles(client, subarguments);
                    break;
                case "listLocalDirectories":
                    command = CommandFactory.createListLocalDirectories(client, subarguments);
                    break;
                case "listLocalFiles":
                    command = CommandFactory.createListLocalFiles(client, subarguments);
                    break;
                case "uploadFile":
                    command = CommandFactory.createUpload(client, subarguments);
                    break;
                case "downloadFile":
                    command = CommandFactory.createDownload(client, subarguments);
                    break;
                case "quit":
                    quitting = true;
                default:
                    client.state.output("Invalid Command");
                    command = CommandFactory.createNull(client, subarguments);
            }
        }
        catch (IllegalArgumentException e)
        {
            System.err.println(e.getMessage());
            command = CommandFactory.createNull(client, subarguments);
        }

        return command;
    }
}