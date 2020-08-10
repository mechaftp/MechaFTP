package edu.pdx.cs;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This class is represents a <code>ConnectionInfo</code>
 * This class has necessary field to create an FTP connection
 * using getter methods.
 */
public class Connection {

    private InetAddress host;
    private Integer port;

    /**
     * Create a new <code>ConnectionInfo</code>
     *
     * @param host This is an IP address or domain address.
     * @param port Holding port value
     */
    public Connection(String host, String port) throws UnknownHostException {
        this(host, Integer.parseInt(port));
    }

    public Connection(String host, int port) throws UnknownHostException {
        this.host = InetAddress.getByName(host);
        if (port < 0 || port > 65535)
            throw new IllegalArgumentException("Valid ports are integer numbers within the range 0 - 65,535. " +
                    "Received: " + port);
        this.port = port;
    }

    /**
     * get IP address
     */
    public InetAddress getHost() {
        return this.host;
    }

    /**
     * get port
     */
    public Integer getPort() {
        return this.port;
    }


    public String toString() {
        return this.host.getHostName() + ":" + this.port;
    }

}
