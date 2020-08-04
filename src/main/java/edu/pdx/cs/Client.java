package edu.pdx.cs;
import org.apache.commons.net.ftp.*;
import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

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

    /**
     * Lists directories w/in the current working directory on remote server
     * @return an array of <code>FTPFile</code> objects
     */
    protected FTPFile[] listRemoteDirectories() throws IOException {
        String path = ftp.printWorkingDirectory();
        return ftp.listDirectories(path);
    }

    /**
     * Lists files w/in the current directory on remote server
     * @return an array of <code>FTPFile</code> objects
     */
    protected FTPFile[] listRemoteFiles() throws IOException{
        String path = ftp.printWorkingDirectory();

        FTPFile[] files =  ftp.listFiles(path, new FTPFileFilter() {
            @Override
            public boolean accept(FTPFile ftpFile) {
                return !ftpFile.isDirectory();
            }
        });

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
     * Returns <code>String</code> of the current working directory
     * @return current working directory
     * @throws IOException
     */
    protected String printWorkingDirectory() throws IOException{
        return ftp.printWorkingDirectory();
    }

    /**
     * Changes the working directory...
     * @param newDir ...to the given directory relative to the current working directory
     * @param cwd The current working directory
     * @return true if the path change was successful, false otherwise
     */
    protected boolean changeDirectory(String newDir, String cwd) throws IOException {
        boolean success = ftp.changeWorkingDirectory(newDir);

        //handles the case in which false is returned due to attempting to
        //"change" into the current working directory
        if(!success)
            success = printWorkingDirectory().equals(cwd + "/" + newDir);

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

    public boolean logout(String username)throws IOException{
        logger.info("User " + username +" is logging out!");
        return ftp.logout();
        //ftp.disconnect();

    }


}
