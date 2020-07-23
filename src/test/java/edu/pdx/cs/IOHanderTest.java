package edu.pdx.cs;

import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.junit.Test;

import java.util.Collection;

public class IOHanderTest {

    IOHandler ioHandler = new IOHandler();

    @Test
    public void ioTest() throws ArgumentParserException {
        Namespace namespace = ioHandler.parser.parseArgs(new String[] { "--login", "username", "password"});
        Collection command = namespace.getAttrs().values();
        //System.out.println(command);
    }
}
