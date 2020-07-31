package edu.pdx.cs;
import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Client {

   private static Logger logger;
    Path logpath;
    FTPClient ftp;

    Client(){
        logger =  LogManager.getLogger(Log4jExample.class);
        ftp = new FTPClient();
    }

    Client(Logger logger, Path logpath, FTPClient ftp){
        this.logger = logger;
        this.logpath = logpath;
        this.ftp = ftp;

    }

    protected void connect (String server, int port) throws IOException {
        ftp.connect(server, port);
    }

    /**
     * Logs users into the FTP server
     * @param username the username
     * @param password the user's password
     * @return true if login is successful, false otherwise
     * @throws IOException
     */
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

    public boolean logout(String username)throws IOException{
        logger.info("User " + username +" is logging out!");
        return ftp.logout();
        //ftp.disconnect();

    }


}
