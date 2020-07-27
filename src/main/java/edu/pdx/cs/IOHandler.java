package edu.pdx.cs;

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
    private ArgumentParser parser;
    private Namespace namespace;
    private List<String> tokens;
    private Client client;
    private boolean validInput;
    public boolean quitting;


    IOHandler(Client client) {
        this.client = client;

        parser = configureArgParser();
    }




    private ArgumentParser configureArgParser()
    {
        ArgumentParser parse = ArgumentParsers.newFor("MechaFTP")
            .addHelp(true)
            .build()
            .description("FTPClient for Agile");
        parse.addArgument( "--login")
            .nargs(2)
            .help("--login username password");
        parse.addArgument("--quit")
            .help("quit MechaFTP");

        return parse;
    }

    /**
     * This should use argparse4j
     * @return Map<String, Object>
     * @throws IOException
     */
    Map<String, Object> getInput() throws IOException {
        String input = this.bufferedReader.readLine();

        try {
            this.namespace = parser.parseArgs(input.split("\\s+"));
        } catch (ArgumentParserException e) {
            this.parser.printHelp();
        }
        String commandString = (String) this.namespace.getAttrs().keySet().toArray()[0];

        // Validate the command
        boolean isValid = false;
        switch (commandString) {
            case "--login":
                isValid = this.validator.dummyValidate();
                break;
        }

        if (!isValid) {
            return null;
        } else {
            return this.namespace.getAttrs();
        }

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

    //TODO: The command factory needs to pass around a reference to the Client, but we don't want to instantiate clients
    // all over the place, and we want to avoid passing the client everywhere too. What's a better pattern for this?
    public ICommand parseInput()
    {
        ICommand command;
        List<String> subarguments = tokens.subList(1, tokens.size());
        switch (tokens.get(0).toLowerCase())
        {
            case "login":
                expectNSubarguments(2);
                command = CommandFactory.createLogin(client);
                command.assignInput(subarguments);
                break;
            case "quit":
                quitting = true;
                break;
        }
        return null;
    }

    private void expectNSubarguments(int N)
    {
        if (this.tokens.size() != N+1)
            throw new IllegalArgumentException("Wrong number of arguments.");
    }
}