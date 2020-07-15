package edu.pdx.cs;

import org.junit.Test;

import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.hamcrest.MatcherAssert.assertThat;


public class ConnectionInfoTest{



    /**
     * Valid port is only two digit number
     */
    @Test
    public void IsValidPort() {
        // MainMethodResult result = invokeMain(new String[] {"Directory path" "IP" "22" "username" });
        ConnectionInfo connection = new ConnectionInfo(Paths.get("Path"), "12.16.254.1", "22", "aang");
        Boolean output = Pattern.matches("[0-90-9]{2}", connection.getPort());
        if (output == false) {
            System.out.println("Invalid port number. Valid port number: 22");
        }
        assertThat(connection.getPort(), output);
    }


    /**
     * IPV4 only allow 255.255.255.255
     * Range is from 0 t0 255
     */
    @Test
    public void IsValidIPV4(){

        ConnectionInfo connection = new ConnectionInfo(Paths.get("Path"), "12.16.254.11", "22", "aang");

        String upperbound255 = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])";

        String ipv4 = upperbound255 + "\\." + upperbound255 + "\\." + upperbound255 + "\\." + upperbound255;

        Pattern p = Pattern.compile(ipv4);

        Boolean result = Pattern.matches(ipv4, connection.getIP());

        if(result == false)
        {
            System.err.println("Invalid IP address");
        }
        assertThat(connection.getIP(), result);

    }


    /**
     * Valid user name contain no space and special character
     *  It can contain numbers
     */
    @Test
    public void IsValidusername()
    {

        ConnectionInfo connection = new ConnectionInfo(Paths.get("Path"), "12.16.254.11", "22", "aang");

        Pattern p = Pattern.compile("^[A-Za-z][A-Za-z0-9]*$");
        Matcher m = p.matcher(connection.getUsername());
        Boolean result = m.find();

        if(result == false)
        {
            System.err.println("Invalid user name. No white space and special character allowed");
        }
        assertThat(connection.getUsername(), result);


    }

}
