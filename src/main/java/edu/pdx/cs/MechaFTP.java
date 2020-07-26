package edu.pdx.cs;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;


import java.io.PrintStream;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MechaFTP
{
    public static PrintStream out = System.out;

    private static Connection currentConnection;

    public static void main(String[] args)
    {
        configureStartup(args);
        run();

        cleanup();
    }

    private static void run()
    {
        CLIStatusBar statusBar = CLIStatusBar.create(System.out, "not connected...");
        statusBar.setRemoteCwd("/data/aang");

        statusBar.render();
//        while(true)
//        {
//
//            if (quitting)
//                break;
//        }
    }

    private static void configureStartup(String[] args)
    {
        Namespace cliArgs = parseStartup(args);

        try
        {
            currentConnection = new Connection(cliArgs.get("host"), (Integer) cliArgs.get("port"));
            out.println("Connecting to " + currentConnection);
        }
        catch (UnknownHostException e)
        {
            out.println("Invalid host detected: " + e.getMessage());
        }
    }

    private static void cleanup()
    {

    }



    private static Namespace parseStartup(String[] args)
    {
        ArgumentParser parser = ArgumentParsers
            .newFor("mechaftp")
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
