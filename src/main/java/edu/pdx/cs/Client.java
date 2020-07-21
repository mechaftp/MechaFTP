package edu.pdx.cs;
//import jdk.nashorn.internal.ir.Block;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.*;

public class Client {

    Path logpath;
    IOHandler ioHandler = new IOHandler();
    FTPClient ftp = new FTPClient();

    protected void connect (String server, int port) throws IOException {
        ftp.connect(server, port);
    }

    protected boolean login (String username, String password) throws IOException {
        return ftp.login(username, password);
    }

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
