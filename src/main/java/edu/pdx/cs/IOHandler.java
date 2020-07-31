package edu.pdx.cs;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class IOHandler {

    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    Validator validator = new Validator();
    ArgumentParser parser;

    IOHandler() {
        parser = ArgumentParsers.newFor("IOHandler").build()
                .description("FTPClient for Agile");
        parser.addArgument( "--login")
                .nargs(2)
                .help("--login username password");
    }

    /**
     * This should use argparse4j
     * @return
     * @throws IOException
     */
    Map<String, Object> getInput() throws IOException {
        String input = bufferedReader.readLine();
        Namespace namespace = null;
        try {
            namespace = parser.parseArgs(input.split(" "));
        } catch (ArgumentParserException e) {
            parser.printHelp();
        }
        String command = (String) namespace.getAttrs().keySet().toArray()[0];

        // Validate the command
        boolean isValid = false;
        switch (command) {
            case "--login":
                isValid = validator.dummyValidate();
                break;
        }

        if (!isValid) {
            return null;
        } else {
            return namespace.getAttrs();
        }

    }
}
