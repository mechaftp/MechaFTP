package edu.pdx.cs;

import edu.pdx.cs.commands.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class IOHandler {
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private final Validator validator = new Validator();
    public List<String> tokens;
    private final Client client;

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
                    command = CommandFactory.createQuit(client, subarguments);
                    break;
                default:
                    client.state.output("Invalid Command");
                    command = CommandFactory.createNull(client, subarguments);
            }
        }
        catch (IllegalArgumentException e)
        {
            client.state.output(e.getMessage());
            command = CommandFactory.createNull(client, subarguments);
        }

        return command;
    }
}