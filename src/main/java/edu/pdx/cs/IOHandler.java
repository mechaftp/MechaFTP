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
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private final ArgumentParser parser;
    private Namespace namespace;
    private List<String> tokens;
    private final Client client;
    public boolean quitting;


    IOHandler(Client client) {
        this.client = client;

        parser = configureArgParser();
    }

    private ArgumentParser configureArgParser() {
        ArgumentParser parse = ArgumentParsers.newFor("MechaFTP")
                .addHelp(true)
                .build()
                .description("FTPClient for Agile");
        parse.addArgument("--login")
                .nargs(2)
                .help("--login username password");
        parse.addArgument("--quit")
                .help("quit MechaFTP");

        return parse;
    }

    /**
     * This should use argparse4j
     *
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
                break;
        }

        if (!isValid) {
            return null;
        } else {
            return this.namespace.getAttrs();
        }

    }

    public void readInput() {
        try {
            String input = bufferedReader.readLine();
            this.tokens = List.of(input.split("\\s+"));
        } catch (IOException | NullPointerException e) {
            this.tokens = List.of();
        }
    }

    public Command parseInput() {
        List<String> subarguments = tokens.subList(1, tokens.size());
        Command command;
        try {
            switch (tokens.get(0).toLowerCase()) {
                case "login":
                    command = CommandFactory.createLogin(client, subarguments);
                    break;
                case "quit":
                    quitting = true;
                default:
                    command = CommandFactory.createNull(client, subarguments);
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            command = CommandFactory.createNull(client, subarguments);
        }

        return command;
    }
}