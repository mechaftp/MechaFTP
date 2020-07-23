package edu.pdx.cs;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;

import static java.lang.System.exit;


/**
 *
 */
public class App {

    static Client client = new Client();
    static Validator validator = new Validator();

    public static void main(String[] args) {
        run(args);
    }

    public static void run(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("Client").build()
                .description("Client for Agile");
        parser.addArgument("-l", "--logfile")
                .setDefault(Paths.get("Logs/"));




        try {
            Namespace namespace = parser.parseArgs(args);
            for (String arg : namespace.getAttrs().keySet()) {
                switch (arg) {
                    case "logfile":
                        if (!validator.validatePath((String) namespace.getAttrs().get(arg))) {
                            System.out.println("Invalid log argument");
                        } else {
                            client.setLogfile(Paths.get((String) namespace.get(arg)));
                        }
                        break;
                    default:
                        System.out.println("This should be unreachable");
                }
            }

            client.run();
        } catch (ArgumentParserException e) {
            e.printStackTrace();
            exit(0);
        }
    }
}
