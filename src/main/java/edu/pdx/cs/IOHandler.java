package edu.pdx.cs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOHandler {

    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));


    /**
     * This should use argparse4j
     * @return
     * @throws IOException
     */
    Command getInput() throws IOException {
        bufferedReader.readLine();


        return Command.UNKNOWN;

    }
}
