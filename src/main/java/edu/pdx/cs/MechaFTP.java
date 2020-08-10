package edu.pdx.cs;

import edu.pdx.cs.commands.Command;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.IOException;
import java.io.PrintStream;

public class MechaFTP {
    public static PrintStream out;

    private static StatusBar statusBar;
    private static IOHandler ioHandler;
    private static Client client;

    public static void main(String[] args) throws IOException {
        startup(args);

        run();

        cleanup();
    }

    private static void startup(String[] args) {
        ArgumentParser parser = ArgumentParsers
                .newFor("MechaFTP")
                .build()
                .version("${prog} v0.1.0");
        parser.addArgument("host")
                .help("Host FTP server to open a connection with.");
        parser.addArgument("port")
                .type(Integer.class)
                .nargs("?")
                .help("Port on the host to connect to");
        parser.addArgument("--version")
                .action(Arguments.version());
        parser.addArgument("--output")
                .help("Set the output destination")
                .type(PrintStream.class)
                .nargs("?")
                .setDefault(System.out);
        parser.addArgument("-l", "--logfile")
                .setDefault("Logs/")
                .help("set the location of the log files");

        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        client = new Client();
        client.connect(ns.get("host"), ns.get("port"));
        out = ns.get("output");
        ioHandler = new IOHandler(client);
        statusBar = StatusBar.create(out);

//        if (ns.get("logfile") != null && !validator.validatePath(ns.get("logfile"))) {
//            System.err.println("Invalid log argument");
//        } else {
//            client.state.setLogFile(Paths.get((String) ns.get("logfile")));
//        }
    }

    private static void run() {
        Command command;
        do {
            // statusBar will use client state to output to user
            statusBar.render(client.state);

            ioHandler.readInput();
            command = ioHandler.parseInput();

            // command may update and/or use client state
            command.execute(client.state);

        } while (!ioHandler.quitting);
    }

    private static void cleanup() {
        statusBar.close();
        // Write logs to file???
    }
}
