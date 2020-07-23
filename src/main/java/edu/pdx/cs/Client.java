package edu.pdx.cs;
import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Client {

    private static Logger logger = LogManager.getLogger(Log4jExample.class);
    Path logpath;
    IOHandler ioHandler = new IOHandler();
    FTPClient ftp = new FTPClient();

    protected void connect (String server, int port) throws IOException {
        ftp.connect(server, port);
    }

    protected boolean login (String username, String password) throws IOException {
        boolean status = ftp.login(username, password);
        if (status) {
            logger.info("Logged in as: ", username);
        } else {
            logger.error("Failed login for: ", username);
        }
        return status;
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
