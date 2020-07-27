package edu.pdx.cs;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MechaFTP
{
    public static PrintStream out;

    private static CLIStatusBar statusBar;
    private static Connection currentConnection;
    private static IOHandler ioHandler;

    public static void main(String[] args)
    {
        configureStartup(args);

        run();

        cleanup();
    }

    private static void run()
    {
        statusBar = CLIStatusBar.create(System.out, "not connected...");
        statusBar.setRemoteCwd("/data/aang");

        statusBar.render();
//        while(true)
//        {
//            ioHandler.getInput();
//            if (quitting)
//                break;
//        }
    }

    private static void configureStartup(String[] args)
    {
        Namespace cliArgs = parseStartup(args);

        try
        {
            out = cliArgs.get("logger");
            ioHandler = new IOHandler();
            currentConnection = new Connection(cliArgs.get("host"), (Integer) cliArgs.get("port"));
        }
        catch (UnknownHostException e)
        {
            out.println("Invalid host detected: " + e.getMessage());
        }
    }

    private static void cleanup()
    {
        statusBar.close();
    }



    private static Namespace parseStartup(String[] args)
    {
        ArgumentParser parser = ArgumentParsers
            .newFor("MechaFTP")
            .build()
            .version("${prog} v0.1.0");
        parser.addArgument("host")
            .help("Host FTP server to open a connection with.");
        parser.addArgument("port")
            .type(Integer.class)
            .nargs("?")
            .help("Port on the host to connect to. Defaults to ")
            .setDefault(20);
        parser.addArgument("--version")
            .action(Arguments.version());
        parser.addArgument("--logger")
            .help("Set the logger output destination")
            .type(PrintStream.class)
            .nargs("?")
            .setDefault(System.out);

        try
        {
            return parser.parseArgs(args);
        }
        catch (ArgumentParserException e)
        {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
