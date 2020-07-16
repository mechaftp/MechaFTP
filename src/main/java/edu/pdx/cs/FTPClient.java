package edu.pdx.cs;
//import jdk.nashorn.internal.ir.Block;
import org.apache.commons.net.ftp.FTP;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;

public class FTPClient {

    Path logpath;
    IOHandler ioHandler = new IOHandler();

    void setLogfile(Path logpath) {
        this.logpath = logpath;
    }

    void postFile(Path serverPath, Path toUpload) {

    }

    void run() {
        /*
        while(true) {
            try {
                ioHandler.getInput();
            } catch (IOException e) {
                e.printStackTrace();
            }

            break;
        }
        */
         
        //cleanup
    }


}
