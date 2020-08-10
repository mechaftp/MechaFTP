package edu.pdx.cs;

import org.junit.Test;

public class CLParseTest {


    @Test
    public void testCLParsingInvalidPath() {
        System.out.println("testing CLparsing of logpath");
        String[] args = new String[]{"--logfile", "doesnotexist"};
        //App.run(args);
        //assertNull(App.client.logpath);
    }


    @Test
    public void testCLParsingValidPath() {
        System.out.println("testing CLparsing of logpath");
        String[] args = new String[]{"--logfile", "logs"};
        //App.run(args);
        //assertEquals(App.client.logpath, Paths.get("logs"));
    }
}
