package edu.pdx.cs;

import edu.pdx.cs.commands.Command;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.PrintStream;
import java.nio.file.Paths;

import static org.fusesource.jansi.Ansi.ansi;

public class MechaFTP
{
    public static PrintStream out;

    private static StatusBar statusBar;
    private static IOHandler ioHandler;
    private static Client client;
    private static Validator validator;

    public static void main(String[] args)
    {
        startup(args);

        run();

        cleanup();
    }

    private static void startup(String[] args)
    {
        Namespace cliArgs = parseStartup(args);

        validator = new Validator();
        client = new Client();
        if (cliArgs != null)
            client.connect(cliArgs.get("host"), cliArgs.get("port"));
        out = cliArgs.get("output");
        ioHandler = new IOHandler(client);
        statusBar = StatusBar.create(out);

        if (!validator.validatePath((String) cliArgs.getAttrs().get("logfile"))) {
            System.out.println("Invalid log argument");
        } else {
            client.setLogfile(Paths.get((String) cliArgs.get("logfile")));
        }
    }

    private static void run()
    {
        Command command;
        do
        {
            statusBar.render(client.state);
            out.print(ansi().fgCyan().a("╚").reset().a(" mechaftp > ").reset());

            ioHandler.readInput();
            command = ioHandler.parseInput();
            command.execute();

        } while (!ioHandler.quitting);
    }

    private static void cleanup()
    {
        statusBar.close();
        // Write logs to file???
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
