package edu.pdx.cs;
//import jdk.nashorn.internal.ir.Block;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.apache.commons.net.ftp.FTP;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FTPClient {

    BlockingQueue<String> commandQueue = new LinkedBlockingQueue<>();
    Path logpath;
    IOHandler ioHandler = new IOHandler();

    void setLogfile(Path logpath) {
        this.logpath = logpath;
    }

    void postFile(Path serverPath, Path toUpload) {

    }

    void run() {
        while(true) {
            try {
                Map input = ioHandler.getInput();
                String command = (String) input.keySet().toArray()[0];

                switch (command) {
                    case "--login":
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            break;
        }
         
        //cleanup
    }


}
