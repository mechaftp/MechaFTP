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
    private Boolean validInput;


    IOHandler() {
        parser = ArgumentParsers.newFor("MechaFTP")
            .addHelp(true)
            .build()
            .description("FTPClient for Agile");
        parser.addArgument( "--login")
            .nargs(2)
            .help("--login username password");
        parser.addArgument("--quit")
            .help("quit MechaFTP");
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

    public Command parseInput()
    {
        Command command;
        switch (tokens.get(0))
        {
            case "login":
                expectNSubarguments(2);
                //command = CommandFactory
        }
        return null;
    }

    private void expectNSubarguments(int N)
    {
        if (this.tokens.size() != N+1)
            validInput = false;
    }
}