package edu.pdx.cs;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    void uploadFile(Path serverPath, Path toUpload) {
        System.out.println(serverPath);
        System.out.println(toUpload);

        String server = "speedtest.tele2.net";
        int port = 21;
        String user = "anonymous";
        String pass = "";

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // APPROACH #1: uploads first file using an InputStream
            String filepath1 = "/Users/David/FTP_UPLOADED1.txt";
            File firstLocalFile = new File(filepath1);

            String firstRemoteFile = "/upload/FTP_UPLOADED1.txt";
            InputStream inputStream = new FileInputStream(firstLocalFile);

            System.out.println("Start uploading first file - " + firstRemoteFile);
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The first file is uploaded successfully.");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    void downloadFile() {
        String server = "speedtest.tele2.net";
        int port = 21;
        String user = "anonymous";
        String pass = "";

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // APPROACH #1: using retrieveFile(String, OutputStream)
            String filepath1 = "1MB.zip";
            String remoteFile1 = "//" + filepath1;
            File downloadFile1 = new File(filepath1);
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
            boolean success = ftpClient.retrieveFile(remoteFile1, outputStream1);
            outputStream1.close();
            //downloadFile1.delete();

            if (success) {
                System.out.println("File #1 has been downloaded successfully.");
            }

//            // APPROACH #2: using InputStream retrieveFileStream(String)
//            String filepath2 = "FTP_DOWNLOADED2.txt";
//            String remoteFile2 = "/test/" + filepath2;
//            File downloadFile2 = new File(filepath2);
//            OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(downloadFile2));
//            InputStream inputStream = ftpClient.retrieveFileStream(remoteFile2);
//            byte[] bytesArray = new byte[4096];
//            int bytesRead = -1;
//            while ((bytesRead = inputStream.read(bytesArray)) != -1) {
//                outputStream2.write(bytesArray, 0, bytesRead);
//            }
//
//            success = ftpClient.completePendingCommand();
//            if (success) {
//                System.out.println("File #2 has been downloaded successfully.");
//            }
//            outputStream2.close();
//            inputStream.close();
//            //downloadFile2.delete();

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
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
