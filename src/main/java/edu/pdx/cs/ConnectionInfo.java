package edu.pdx.cs;

import java.nio.file.Path;

/**
 * This class is represents a <code>ConnectionInfo</code>
 * This class has necessary field to create an FTP connection
 * using getter methods.
 */
public class ConnectionInfo {

    private Path currentDir;
    private String IP;
    private String port;
    private String username;

    public ConnectionInfo() {}


    /**
     * Create a new <code>ConnectionInfo</code>
     * @param currentDir
     *        This Directory path
     * @param IP
     *        This is IP address.
     * @param port
     *        Holding port value
     * @param username
     *        Holding username value
     */
    public ConnectionInfo(Path currentDir, String IP, String port, String username){
        this.currentDir = currentDir;
        this.IP = IP;
        this.port = port;
        this.username = username;
    }



    /**
     * get currentDir
     */
    public Path getCurrentDir(){ return currentDir; };

    /**
     * get IP address
     */
    public String getIP(){ return IP; };

    /**
     * get port
     */
    public String getPort(){ return port; };


    /**
     * get username
     */
    public String getUsername(){return username;};









}
