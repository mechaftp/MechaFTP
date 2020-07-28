package edu.pdx.cs;
import org.apache.commons.net.ftp.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Client {

    private static Logger logger = LogManager.getLogger(Log4jExample.class);
    Path logpath;
    FTPClient ftp = new FTPClient();

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

    /**
     * Lists directories on remote server
     * @return an array of <code>FTPFile</code> objects
     */
    protected FTPFile[] listRemoteDirectories(){
        FTPFile[] directories = null;

        try {
            directories = ftp.listDirectories();
        } catch (IOException e) {
            System.err.println("\nIO Error\n");
            e.printStackTrace();
        }

        return directories;
    }

    /**
     * Lists files on remote server
     * @return an array of <code>FTPFile</code> objects
     */
    protected FTPFile[] listRemoteFiles(){
        FTPFile[] files = null;

        try {
            files = ftp.listFiles();
        } catch (IOException e) {
            System.err.println("\nIO Error\n");
            e.printStackTrace();
        }

        return files;
    }

    /**
     * Converts names of <code>FTPFile</code> objects to strings
     * @param files array of <code>FTPFiles</code> objects
     * @return <code>ArrayList</code> of names in <code>String</code> format
     */
    protected ArrayList<String> fileDirectoryListStrings(FTPFile[] files){
       ArrayList<String> names = null;

       for(FTPFile file:files)
           names.add(file.getName());

       return names;
    }

    /**
     * Changes the working directory...
     * @param path ...to the given file path
     * @return true if the path change was successful, false otherwise
     */
    protected boolean changeDirectory(String path){
        boolean success = false;
        try {
            success = ftp.changeWorkingDirectory(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;
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
