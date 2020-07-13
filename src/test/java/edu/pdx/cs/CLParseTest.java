package edu.pdx.cs;

import org.junit.After;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CLParseTest {

    @After
    public void teardown() {
        App.client = new FTPClient();
    }

    @Test
    public void testCLParsingInvalidPath() {
        System.out.println("testing CLparsing of logpath");
        String[] args = new String[]{"--logfile", "doesnotexist"};
        App.run(args);
        assertNull(App.client.logpath);
    }


    @Test
    public void testCLParsingValidPath() {
        System.out.println("testing CLparsing of logpath");
        String[] args = new String[]{"--logfile", "logs"};
        App.run(args);
        assertEquals(App.client.logpath, Paths.get("logs"));
    }
}
