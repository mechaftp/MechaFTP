package edu.pdx.cs;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Client {

    private static Logger logger;
    public final ClientState state;
    final FTPClient ftp;

    Client() {
        logger = LogManager.getLogger(Client.class);
        ftp = new FTPClient();
        state = new ClientState();
    }

    Client(Logger logger, FTPClient ftp, ClientState state) {
        Client.logger = logger;
        this.ftp = ftp;
        this.state = state;
    }

    public void connect(String server, int port) {
        connect(server, Integer.valueOf(port));
    }

    public void connect(String server, Integer port) {
        try {
            if (port != null)
                ftp.connect(server, port);
            else
                ftp.connect(server);
            // TODO: state.setRemoteCwd to the current working directory on server
            state.setRemoteCwd(Paths.get("/data/aang"));
        } catch (IOException e) {
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
    public boolean login(String username, String password) throws IOException {
        boolean status = ftp.login(username, password);
        if (status) {
            logger.info("Logged in as: " + username);
        } else {
            logger.error("Failed login for: " + username);
        }
        return status;
    }

    /**
     * Lists directories w/in the current working directory on remote server
     *
     * @return an array of <code>FTPFile</code> objects
     */
    public FTPFile[] listRemoteDirectories() throws IOException {
        String path = ftp.printWorkingDirectory();
        FTPFile[] directories = ftp.listDirectories(path);

        //log
        if (directories == null)
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
     *
     * @return an array of <code>FTPFile</code> objects
     */
    public FTPFile[] listRemoteFiles() throws IOException {
        String path = ftp.printWorkingDirectory();

        FTPFile[] files = ftp.listFiles(path, ftpFile -> !ftpFile.isDirectory());

        //log
        if (files == null)
            logger.info("No files from remote server added to list");
        else {
            for (FTPFile file : files)
                logger.info("File " + file.getName() + " from remote server added to list");
        }

        return files;
    }

    /**
     * Converts names of <code>FTPFile</code> objects to strings
     *
     * @param files array of <code>FTPFiles</code> objects
     * @return <code>ArrayList</code> of names in <code>String</code> format
     */
    public ArrayList<String> fileDirectoryListStrings(FTPFile[] files) {
        ArrayList<String> names = null;

        if (files == null) {
            return null;
        }

        for (FTPFile file : files)
            names.add(file.getName());

        return names;
    }

    /**
     * Returns <code>String</code> of the current working directory
     *
     * @return current working directory
     * @throws IOException If the FTP connection is closed unexpectedly or
     *                     if an error occurs while sending to or receiving from the server.
     */
    public String printWorkingDirectory() throws IOException {
        return ftp.printWorkingDirectory();
    }

    /**
     * Changes the working directory...
     *
     * @param dir ...to the given directory relative to the current working directory
     * @return true if the path change was successful, false otherwise
     */
    public boolean changeDirectory(String dir) {
        boolean success = false;

        try {
            success = ftp.changeWorkingDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (success)
            logger.info("Changed to directory " + dir + " on remote server");
        else
            logger.error("Failed to change to directory " + dir + " on remote server");

        return success;
    }

    /**
     * Determines directories w/in the current working directory on local machine
     *
     * @return an <code>ArrayList</code> of directory names w/in the cwd
     */
    public ArrayList<String> listDirectoriesLocal() {
        File cwd = new File(state.getLocalCwdString());
        File[] directories = cwd.listFiles();
        ArrayList<String> dirNames = new ArrayList<>();


        if (directories == null)
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
     *
     * @return an <code>ArrayList</code> of file names w/in the cwd
     */
    public ArrayList<String> listFilesLocal() {
        File cwd = new File(state.getLocalCwdString());
        File[] files = cwd.listFiles();
        ArrayList<String> fileNames = new ArrayList<>();

        if (files == null)
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

    /**
     * This function retrieves a file from the server.
     *
     * @param file file name in the remote server
     * @return
     * @throws IOException
     */
    public boolean retrieveFile(String file) throws IOException {
        FileOutputStream output = new FileOutputStream(file);

        if (!ftp.retrieveFile(file, output)) {
            logger.error("Can't download file!");
            return false;
        }

        output.close();
        logger.info("File" + file + " retrieved from the server!");
        return true;
    }

    /**
     * This function uploads a files to the server
     *
     * @param file
     * @return
     * @throws IOException
     */
    public boolean uploadFile(File file) throws IOException {

        if (!file.exists()) {
            logger.error("Passed File not created on local machine. It can't be upload to sever");
            return false;
        }

        FileInputStream input = new FileInputStream(file);

        if ((!ftp.storeFile(file.getName(), input))) {
            logger.error("File  " + file.getName() + " can't upload ");
            return false;
        }

        input.close();
        logger.info("File " + file.getName() + " uploaded ");
        return true;
    }


    /**
     * Logs the username off and outputs logging out message
     *
     * @param username
     * @return
     * @throws IOException
     */
    public boolean logout(String username) throws IOException {
        logger.info("User " + username + " is logging out!");
        return ftp.logout();

    }


}
