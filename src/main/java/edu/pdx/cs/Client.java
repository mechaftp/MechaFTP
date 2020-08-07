package edu.pdx.cs;

import org.apache.commons.net.ftp.*;
import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.util.stream.Stream;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class Client {

    private static Logger logger;
    public ClientState state;
    Path logpath;
    FTPClient ftp;

    Client(){
        logger =  LogManager.getLogger(Log4jExample.class);
        ftp = new FTPClient();
        state = new ClientState();
    }

    Client(Logger logger, Path logpath, FTPClient ftp){
        this.logger = logger;
        this.logpath = logpath;
        this.ftp = ftp;
        this.state = new ClientState();
    }

    public void connect(String server, int port)
    {
        connect(server, Integer.valueOf(port));
    }

    public void connect(String server, Integer port)
    {
        try
        {
            if (port != null)
                ftp.connect(server, port);
            else
                ftp.connect(server);
            // TODO: state.setRemoteCwd to the current working directory on server
            state.setRemoteCwd(Paths.get("/data/aang"));
        }
        catch (IOException e)
        {
            logger.error("Failed to connect to server " + server + " with error:\n" + e.getLocalizedMessage());
            state.setRemoteCwd(Path.of(""));
        }

    }

    /**
     * Logs users into the FTP server
     *
     * @param username the username
     * @param password the user's password
     * @return true if login is successful, false otherwise
     * @throws IOException
     */
    public boolean login (String username, String password) throws IOException {
        boolean status = ftp.login(username, password);
        if (status) {
            logger.info("Logged in as: " + username);
        } else {
            logger.error("Failed login for: " + username);
        }
        return status;
    }

    void setLogfile(Path logpath)
    {
        this.logpath = logpath;
    }

    /**
     * Lists directories w/in the current working directory on remote server
     * @return an array of <code>FTPFile</code> objects
     */
    protected FTPFile[] listRemoteDirectories() throws IOException {
        String path = ftp.printWorkingDirectory();
        FTPFile[] directories = ftp.listDirectories(path);

        //log
        if(directories == null)
            logger.info("No directories from remote server added to list");
        else {
            for (FTPFile dir : directories) {
                logger.info("Directory " + dir.getName() + " from remote server added to list");
            }
        }
        
        return directories;
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

        //log
        if(files == null)
            logger.info("No files from remote server added to list");
        else{
            for(FTPFile file : files)
               logger.info("File " + file.getName() + " from remote server added to list");
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
     * @return true if the path change was successful, false otherwise
     */
    protected boolean changeDirectory(String newDir){
        boolean success = false;

        //get relative name of current directory
        String[] split = state.getLocalCwdString().split("/");
        String cwd = split[split.length-1];  //i.e. the last directory name in the hierarchy

        //if new directory is same as the current working directory
        if(newDir == cwd)
            success = true;
        //otherwise...
        else {
            try {
                success = ftp.changeWorkingDirectory(newDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //log
        if(success)
            logger.info("Changed to directory " + newDir + " on remote server");
        else
            logger.error("Failed to change to directory " + newDir + " on remote server");

        return success;
    }

    /**
     * Determines directories w/in the current working directory on local machine
     * @return an <code>ArrayList</code> of directory names w/in the cwd
     */
    public ArrayList<String> listDirectoriesLocal(){
        File cwd = new File(state.getLocalCwdString());
        File[] directories = cwd.listFiles();
        ArrayList<String> dirNames = new ArrayList<>();


        if(directories == null)
            logger.info("No directories from local machine added to list");
        else {
            for (File dir : directories) {
                if (dir.isDirectory())
                    dirNames.add(dir.getName());
                logger.info("Directory " + dir.getName() + " from local machine added to list");
            }
        }

        return dirNames;
    }

    /**
     * Determines files w/in the current working directory on local machine
     * @return an <code>ArrayList</code> of file names w/in the cwd
     */
    public ArrayList<String> listFilesLocal(){
        File cwd = new File(state.getLocalCwdString());
        File[] files = cwd.listFiles();
        ArrayList<String> fileNames = new ArrayList<>();

        if(files == null)
            logger.info("No files from local machine added to list");
        else {
            for (File file : files) {
                if (!file.isDirectory())
                    fileNames.add(file.getName());
                logger.info("File " + file.getName() + " from local machine added to list");
            }
        }

        return fileNames;
    }

    public boolean logout(String username)throws IOException{
        logger.info("User " + username +" is logging out!");
        return ftp.logout();
        //ftp.disconnect();

    }


}
