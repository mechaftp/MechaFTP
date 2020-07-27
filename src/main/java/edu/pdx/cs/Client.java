package edu.pdx.cs;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.nio.file.Path;

public class Client
{
    public static Logger logger = LogManager.getLogger(Log4jExample.class);
    Path logpath;
    IOHandler ioHandler = new IOHandler(this);
    FTPClient ftp = new FTPClient();

    /**
     * Logs users into the FTP server
     * @param username the username
     * @param password the user's password
     * @return true if login is successful, false otherwise
     * @throws IOException
     */
    public boolean login (String username, String password) throws IOException {
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
